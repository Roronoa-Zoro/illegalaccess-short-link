package com.illegalaccess.link.sequence.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SequenceVO {
    private Long sequence; //
    private int sequenceLeft; // 剩余数字的数量
}
