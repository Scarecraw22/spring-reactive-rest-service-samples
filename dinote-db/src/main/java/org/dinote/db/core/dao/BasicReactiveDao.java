package org.dinote.db.core.dao;

import org.reactivestreams.Publisher;

public interface BasicReactiveDao<T, R> {

    Publisher<T> findById(final R id);

    Publisher<T> deleteById(final R id);

    Publisher<T> save(final T entity);
}
