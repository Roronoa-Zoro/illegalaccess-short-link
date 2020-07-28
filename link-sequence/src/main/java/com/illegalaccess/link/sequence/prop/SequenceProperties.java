package com.illegalaccess.link.sequence.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "sequence")
public class SequenceProperties {

    /**
     * key是appkey
     * value是shardingId配置
     */
    private Map<String, ShardingIdConfig> prop = new HashMap<>();



}
