package org.dinote.db.core.dao;

import reactor.core.publisher.Mono;

public interface BasicReactiveDao<T, R> {

    Mono<T> findById(R id);

    Mono<T> deleteById(R id);

    Mono<T> save(T entity);
}
