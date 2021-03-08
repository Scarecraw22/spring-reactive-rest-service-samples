package org.dinote.db.salt.dao;

import lombok.RequiredArgsConstructor;
import org.dinote.db.core.query.string.StringQueryBuilderFactory;
import org.dinote.db.salt.converter.SaltConverter;
import org.dinote.db.salt.entity.Salt;
import org.reactivestreams.Publisher;
import org.springframework.r2dbc.core.DatabaseClient;

@RequiredArgsConstructor
public class SaltR2dbcDao implements SaltReactiveDao {

    private final DatabaseClient client;
    private final SaltConverter saltConverter;
    private final StringQueryBuilderFactory queryFactory;

    @Override
    public Publisher<Salt> getSalt() {
        return client.sql(queryFactory.create()
                                  .selectAll()
                                  .from("dinote", "salt")
                                  .build())
                .map(saltConverter)
                .one();
    }
}
