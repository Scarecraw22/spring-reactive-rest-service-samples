package org.dinote.db.core.dao;

import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

public interface BasicReactiveDao<T, R> {

    Mono<T> findById(@NotNull final R id);

    Mono<T> deleteById(@NotNull final R id);

    Mono<T> save(@NotNull final T entity);
}
