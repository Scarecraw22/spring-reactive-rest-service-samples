package org.dinote.rest.initializers;

import org.dinote.rest.test.containers.RedisTestContainer;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class RedisIntegrationTestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        RedisTestContainer redisTestContainer = RedisTestContainer.getInstance();
        redisTestContainer.startWithStopOnShutdown();

        TestPropertyValues.of("redis.url=" + redisTestContainer.getRedisUrl())
                .and(defaultProperties())
                .applyTo(applicationContext.getEnvironment());
    }

    public List<String> defaultProperties() {
        return List.of();
    }
}
