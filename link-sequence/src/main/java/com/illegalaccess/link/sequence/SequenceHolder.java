package com.illegalaccess.link.sequence;

import com.illegalaccess.link.sequence.dao.SequenceEmitterDao;
import com.illegalaccess.link.sequence.dto.SequenceVO;
import com.illegalaccess.link.sequence.entity.SequenceEmitterEntity;
import com.illegalaccess.link.sequence.prop.SequenceMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
/**
* 提供获取序列的功能
* 提供从数据库加载下一批序列的功能
*/
@Component
public class SequenceHolder {

    @Autowired
    private SequenceMeta sequenceMeta;
    @Autowired
    private SequenceEmitterDao sequenceEmitterDao;
    // key是appkey，value是对应的双端队列
    private Map<String, BlockingDeque<Long>> appKeySequence = new ConcurrentHashMap<>();
    // key时appkey，value时对应上面双端队列里面剩余数字序列的个数
    private Map<String, AtomicInteger> dequeCapacity = new ConcurrentHashMap<>();
    
    /**
    * 取appKey的下一个序列，无序列时返回null
    */
    public SequenceVO getNextSequence(String appKey) {
         Long sequence = appKeySequence.get(appKey).poll();
         return buildSequenceVO(appKey, sequence);
    }
    
    /**
    * 阻塞的取appKey的下一个序列
    */
    public SequenceVO getNextSequenceWithBlocking(String appKey) throws InterruptedException {
        Long sequence = appKeySequence.get(appKey).takeFirst();
        return buildSequenceVO(appKey, sequence);
    }

    private SequenceVO buildSequenceVO(String appKey, Long sequence) {
        int sequenceLeft;
        if (sequence == null) {
            return null;
        }
        sequenceLeft = dequeCapacity.get(appKey).decrementAndGet();
        return SequenceVO.builder().sequence(sequence).sequenceLeft(sequenceLeft).build();
    }

    /**
    * 根据appKey，从数据库加载下一批序列
    *
    */
    public boolean reloadSequence(String appKey) {
        List<Long> shardingIds = sequenceMeta.getAppShardingId(appKey);
        int randomMode = ThreadLocalRandom.current().nextInt(shardingIds.size());
        Long shardingId = shardingIds.get(randomMode + 1);
        SequenceEmitterEntity sequenceEmitterEntity = sequenceEmitterDao.queryByShardingId(shardingId);
        int fetchSize = sequenceMeta.getFetchSize(appKey);
        int stepVal = sequenceEmitterEntity.getStepVal();
        long incr = fetchSize * stepVal;
        int updated = sequenceEmitterDao.updateSequence(shardingId, sequenceEmitterEntity.getCurrentVal(), incr);
        if (updated != 1) {
            return false;
        }

        BlockingDeque<Long> deque = appKeySequence.get(appKey);
        if (deque == null) {
            deque = new LinkedBlockingDeque<>();
            appKeySequence.put(appKey, deque);
            dequeCapacity.put(appKey, new AtomicInteger(0));
        }
        AtomicInteger capacity = dequeCapacity.get(appKey);
        int start = 1;
        long currentVal = sequenceEmitterEntity.getCurrentVal();
        while (start <= fetchSize) {
            currentVal += stepVal;
            deque.addLast(currentVal);
            capacity.incrementAndGet();
            start++;
        }
        return true;
    }
}
