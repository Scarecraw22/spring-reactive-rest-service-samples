package org.dinote.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class DinoteCoreConfiguration {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
