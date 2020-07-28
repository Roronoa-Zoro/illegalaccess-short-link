package com.illegalaccess.link.sequence.parser;

import java.util.List;

public interface ShardingIdParser {
    /**
     * 解析表达式
     * @param expression
     * @return
     */
    List<Long> parseExpression(String expression);
}
