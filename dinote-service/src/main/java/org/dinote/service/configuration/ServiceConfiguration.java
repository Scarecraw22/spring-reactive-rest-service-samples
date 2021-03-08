package org.dinote.service.configuration;

import org.dinote.db.salt.dao.SaltReactiveDao;
import org.dinote.service.password.PasswordHasher;
import org.dinote.service.password.PasswordService;
import org.dinote.service.password.PasswordServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public PasswordHasher passwordHasher() {
        return new PasswordHasher();
    }

    @Bean
    public PasswordService passwordService(PasswordHasher passwordHasher,
                                           SaltReactiveDao saltReactiveDao) {
        return new PasswordServiceImpl(passwordHasher, saltReactiveDao);
    }
}
