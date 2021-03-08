package org.dinote.db.core.dao;

import org.reactivestreams.Publisher;

public interface BasicReactiveDao<T, R> {

    Publisher<T> findById(R id);

    Publisher<T> deleteById(R id);

    Publisher<T> save(T entity);
}
