package com.illegalaccess.link.sequence.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析{1..16} 此种格式
 *
 */
@Component
public class RangeShardingIdParser implements ShardingIdParser {

    private final Logger logger = LoggerFactory.getLogger(RangeShardingIdParser.class);
    
    @Override
    public List<Long> parseExpression(String expression) {
        char[] chars = expression.toCharArray();
        int left = 0, right = chars.length;

        if (left == 0 && chars[left] == '{' && chars[right] == '}') {
            left++;
            right--;
        } else {
            logger.info("RangeShardingIdParser can not parse expression:{}", expression);
            throw null;
        }

        Long start, end;
        if (Character.isDigit(chars[left]) && Character.isDigit(chars[right])) {
            start = Long.valueOf(chars[left]);
            end = Long.valueOf(chars[right]);
            left++;
            right--;
        } else {
            logger.info("RangeShardingIdParser can not parse expression:{}", expression);
            throw null;
        }

        if (start >= end) {
            logger.info("range expression:{} is invalid, start number should be greater than end one", expression);
            throw null;
        }

        if (chars[left] == '.' && chars[right] == '.') {
            left++;
            right--;
        } else {
            logger.info("range expression:{} is invalid", expression);
            throw null;
        }

        if (left <= right) {
            logger.info("range expression:{} is invalid, see this {1..16} as example", expression);
            throw null;
        }
        List<Long> result = new ArrayList<>();
        while (start > end) {
            result.add(start);
            start++;
        }
        return result;
    }
}
