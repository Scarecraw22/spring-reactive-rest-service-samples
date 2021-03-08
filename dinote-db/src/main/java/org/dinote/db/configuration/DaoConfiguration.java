package org.dinote.db.configuration;

import org.dinote.db.core.query.string.PsqlStringQueryBuilderFactory;
import org.dinote.db.core.query.string.StringQueryBuilderFactory;
import org.dinote.db.salt.converter.SaltConverter;
import org.dinote.db.salt.dao.SaltR2dbcDao;
import org.dinote.db.salt.dao.SaltReactiveDao;
import org.dinote.db.user.converter.UserConverter;
import org.dinote.db.user.dao.UserR2dbcDao;
import org.dinote.db.user.dao.UserReactiveDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.core.DatabaseClient;

import java.time.Clock;

@Configuration
public class DaoConfiguration {

    @Bean
    public StringQueryBuilderFactory stringQueryBuilderFactory() {
        return new PsqlStringQueryBuilderFactory();
    }

    @Bean
    public SaltConverter saltConverter() {
        return new SaltConverter();
    }

    @Bean
    public UserConverter userConverter() {
        return new UserConverter();
    }

    @Bean
    public SaltReactiveDao saltReactiveDao(DatabaseClient databaseClient,
                                           SaltConverter saltConverter,
                                           StringQueryBuilderFactory stringQueryBuilderFactory) {
        return new SaltR2dbcDao(databaseClient, saltConverter, stringQueryBuilderFactory);
    }

    @Bean
    public UserReactiveDao userReactiveDao(DatabaseClient databaseClient,
                                           UserConverter userConverter,
                                           Clock clock,
                                           StringQueryBuilderFactory stringQueryBuilderFactory) {
        return new UserR2dbcDao(databaseClient, userConverter, clock, stringQueryBuilderFactory);
    }
}
