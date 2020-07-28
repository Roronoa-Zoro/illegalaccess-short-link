package com.illegalaccess.link.sequence.test;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.*;

public class SequenceTest {

    @Test
    public void mapTest() {
        Map<String, BlockingDeque<Long>> appKeySequence = new ConcurrentHashMap<>();
        BlockingDeque<Long> queue = appKeySequence.getOrDefault("demo", new LinkedBlockingDeque<>(10));
        System.out.println(queue);
        System.out.println(appKeySequence.get("demo"));
    }

    @Test
    public void randomTest() {
        for (int i = 0; i < 16; i++) {
            System.out.println(ThreadLocalRandom.current().nextInt(2));
        }


    }
}
