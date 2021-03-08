package org.dinote.db;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class TestApplication {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
