package org.dinote.db.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public Flyway flyway(@Value("${spring.r2dbc.url}") String url,
                         @Value("${spring.r2dbc.username}") String username,
                         @Value("${spring.r2dbc.password}") String password,
                         @Value("${spring.flyway.enabled}") boolean flywayEnabled) {

        if (flywayEnabled) {
            return new Flyway(Flyway.configure()
                                      .baselineOnMigrate(true)
                                      .dataSource(url.replace("r2dbc", "jdbc"), username, password));
        } else {
            return null;
        }
    }
}
