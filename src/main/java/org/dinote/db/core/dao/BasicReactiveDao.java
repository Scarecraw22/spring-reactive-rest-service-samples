package org.dinote.db.core.dao;

import reactor.core.publisher.Mono;

public interface BasicReactiveDao<T, R> {

    Mono<T> findById(final R id);

    Mono<T> deleteById(final R id);

    Mono<T> save(final T entity);
}
