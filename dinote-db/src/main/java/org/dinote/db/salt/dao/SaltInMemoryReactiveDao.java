package org.dinote.db.salt.dao;

import org.dinote.db.salt.entity.Salt;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

public class SaltInMemoryReactiveDao implements SaltReactiveDao {
    @Override
    public Publisher<Salt> getSalt() {
        return Mono.just(Salt.builder()
                                 .hash("salt-from-memory")
                                 .build());
    }
}
