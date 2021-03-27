package org.dinote.configuration;

import org.dinote.db.core.query.string.PsqlStringQueryBuilderFactory;
import org.dinote.db.core.query.string.StringQueryBuilderFactory;
import org.dinote.db.salt.converter.SaltConverter;
import org.dinote.db.salt.dao.SaltR2dbcDao;
import org.dinote.db.salt.dao.SaltReactiveDao;
import org.dinote.db.user.converter.UserConverter;
import org.dinote.db.user.dao.UserR2dbcDao;
import org.dinote.db.user.dao.UserReactiveDao;
import org.dinote.db.user.validator.UserValidator;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.core.DatabaseClient;

import java.time.Clock;

@Configuration
public class DbConfig {

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

    @Bean
    public StringQueryBuilderFactory stringQueryBuilderFactory() {
        return new PsqlStringQueryBuilderFactory();
    }

    @Bean
    public SaltReactiveDao saltReactiveDao(DatabaseClient databaseClient,
                                           SaltConverter saltConverter,
                                           StringQueryBuilderFactory stringQueryBuilderFactory) {
        return new SaltR2dbcDao(databaseClient, saltConverter, stringQueryBuilderFactory);
    }

    @Bean
    public UserValidator userValidator() {
        return new UserValidator();
    }

    @Bean
    public UserReactiveDao userReactiveDao(DatabaseClient databaseClient,
                                           UserConverter userConverter,
                                           Clock clock,
                                           StringQueryBuilderFactory stringQueryBuilderFactory,
                                           UserValidator userValidator) {
        return new UserR2dbcDao(
                databaseClient,
                userConverter,
                clock,
                stringQueryBuilderFactory,
                userValidator);
    }
}
