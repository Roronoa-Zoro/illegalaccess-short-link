package com.illegalaccess.link.sequence.prop;

import com.illegalaccess.link.sequence.parser.ShardingIdParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SequenceMeta {

    @Autowired
    private SequenceProperties sequenceProperties;
    @Autowired
    private List<ShardingIdParser> parsers;

    // key是appkey，value是在数据库中的shardingId
    private Map<String, List<Long>> appKeyShardingId = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        sequenceProperties.getProp().forEach((key, val) -> {
            List<Long> shardingIds = parseShardingId(val.getShardingIdExpression());
            if (CollectionUtils.isEmpty(shardingIds)) {
                throw new IllegalArgumentException("expression is invalid");
            }

            appKeyShardingId.put(key, shardingIds);
        });
    }

    public int getFetchSize(String appKey) {
        return sequenceProperties.getProp().get(appKey).getFetchSize();
    }

    public int getRefetchThreshold(String appKey) {
        return sequenceProperties.getProp().get(appKey).getRefetchThreshold();
    }

    private List<Long> parseShardingId(String expression) {
        for (ShardingIdParser parser : parsers) {
            List<Long> shardingIds = parser.parseExpression(expression);
            if (!CollectionUtils.isEmpty(shardingIds)) {
                return shardingIds;
            }
        }

        return null;
    }

    public List<Long> getAppShardingId(String appKey) {
        return appKeyShardingId.get(appKey);
    }

    public Set<String> getAllAppKey() {
        return sequenceProperties.getProp().keySet();
    }
}
