package org.dinote.db.salt.dao;

import org.dinote.db.core.query.string.StringQueryBuilderFactory;
import org.dinote.db.salt.converter.SaltConverter;
import org.dinote.db.salt.entity.Salt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class SaltR2dbcDao implements SaltReactiveDao {

    private final DatabaseClient client;
    private final SaltConverter saltConverter;
    private final StringQueryBuilderFactory queryFactory;

    @Autowired
    public SaltR2dbcDao(DatabaseClient client,
                        SaltConverter saltConverter,
                        StringQueryBuilderFactory queryFactory) {
        this.client = client;
        this.saltConverter = saltConverter;
        this.queryFactory = queryFactory;
    }

    @Override
    public Mono<Salt> getSalt() {
        return client.sql(queryFactory.create()
                                  .selectAll()
                                  .from("dinote", "salt")
                                  .build())
                .map(saltConverter)
                .one();
    }
}
