package com.illegalaccess.link.sequence.prop;

import lombok.Data;

@Data
public class ShardingIdConfig {

    // shardingId的表达式，如:{1..16}, [1,3,5,7]
    private String shardingIdExpression;
    // 一次拉取多少个数字序列
    private int fetchSize = 200;
    // 当前剩余的数字序列少于此值时拉取下一批
    private int refetchThreshold = 20;
}
