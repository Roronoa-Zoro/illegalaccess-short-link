package com.illegalaccess.link.sequence.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 发号器实体
 */
@Data
public class SequenceEmitterEntity {
    // pk,
    private Long shardingId;
    // 当前值
    private Long currentVal;
    // 每次递增得值
    private int stepVal;

    private String appKey;

    private LocalDateTime createTime;
}
