package com.illegalaccess.link.sequence;

import com.illegalaccess.link.sequence.dto.SequenceVO;
import com.illegalaccess.link.sequence.event.LoadSequenceEvent;
import com.illegalaccess.link.sequence.prop.SequenceMeta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
@Slf4j
@Component
public class SequenceEmitter implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private SequenceHolder sequenceHolder;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private SequenceMeta sequenceMeta;


    // 是否加载下一批序列
    private AtomicBoolean loadingSequence = new AtomicBoolean(false);
    // key是appkey,value是对应的双端队列的容量的3背，队列容量通过表达式计算
    private Map<String, AtomicInteger> appKeySequenceCapacity = new ConcurrentHashMap<>();

    private Map<String, AtomicBoolean> appKeyLoadFlag = new ConcurrentHashMap<>();

    public Long getNextSequence(String appKey) {
        SequenceVO sequenceVO = sequenceHolder.getNextSequence(appKey);
        if (sequenceVO != null) {
            int refetchThreshold = sequenceMeta.getRefetchThreshold(appKey);
            // 已经低于阈值，需要预加载下一批序列数字
            if (sequenceVO.getSequenceLeft() <= refetchThreshold) {
                AtomicBoolean reloadFlag = appKeyLoadFlag.computeIfAbsent(appKey, key -> new AtomicBoolean(false));
                boolean set = reloadFlag.compareAndSet(false, true);
                // 放并发，cas操作成功才发送加载事件，保证事件只发送一次
                if (set) {
                    publisher.publishEvent(new LoadSequenceEvent(appKey));
                }
            }

            return sequenceVO.getSequence();
        }

        // 此appkey无序列，可能重新加载耗时过长，导致剩余的序列被耗尽
        AtomicBoolean reloadFlag = appKeyLoadFlag.computeIfAbsent(appKey, key -> new AtomicBoolean(false));
        boolean set = reloadFlag.compareAndSet(false, true);
        // 放并发，cas操作成功才发送加载事件，保证事件只发送一次
        if (set) {
            publisher.publishEvent(new LoadSequenceEvent(appKey));
        }
        try {
            sequenceVO = sequenceHolder.getNextSequenceWithBlocking(appKey);
        } catch (InterruptedException e) {
            log.error("get sequence for appkey:{} fail", appKey, e);
            return null;
        }

        return sequenceVO.getSequence();
    }

    /**
     * 程序启动后从db加载一次
     * @param contextRefreshedEvent
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        sequenceMeta.getAllAppKey().forEach(appKey -> {
            sequenceHolder.reloadSequence(appKey);
        });
    }

    @Async("loadSequenceExecutor")
    @EventListener
    public void onSequenceReloadEvent(LoadSequenceEvent event) {
        log.info("will fetch sequence for appkey:{}", event.getAppKey());
        sequenceHolder.reloadSequence(event.getAppKey());
        AtomicBoolean flag = appKeyLoadFlag.get(event.getAppKey());
        flag.set(false);
    }
}
