package org.dinote.db.salt.dao;

import org.dinote.db.salt.entity.Salt;
import reactor.core.publisher.Mono;

public interface SaltReactiveDao {

    Mono<Salt> getSalt();
}
