package com.illegalaccess.link.sequence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class SequenceConfig {

    @Bean("loadSequenceExecutor")
    public Executor myTaskAsyncPool() {
        return Executors.newSingleThreadExecutor();
    }
}
