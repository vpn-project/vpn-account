package com.nesterrovv.vpnaccount.configuration;

import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration
public class SchedulerConfiguration {
    private final Integer connectionPoolSize;

    public SchedulerConfiguration(@Value("${spring.datasource.maximum-pool-size}") Integer connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
    }

    @Bean
    public Scheduler jdbcScheduler() {
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(connectionPoolSize));
    }
}
