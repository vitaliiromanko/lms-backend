package com.portfolio.lmsbackend.config.attempt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class AttemptSchedulerConfig {

    @Bean
    public ThreadPoolTaskScheduler attemptTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(20);
        scheduler.setThreadNamePrefix("AttemptScheduler-");
        scheduler.initialize();
        return scheduler;
    }
}
