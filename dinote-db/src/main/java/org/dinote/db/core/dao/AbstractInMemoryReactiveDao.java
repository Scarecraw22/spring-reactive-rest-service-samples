package org.dinote.db.core.dao;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

abstract public class AbstractInMemoryReactiveDao<T, R> implements BasicReactiveDao<T, R> {

    protected final Map<R, T> database;

    protected AbstractInMemoryReactiveDao() {
        this.database = new HashMap<>();
    }

    @Override
    public Publisher<T> findById(R id) {
        return database.containsKey(id) ? Mono.just(database.get(id)) : Mono.empty();
    }

    @Override
    public Publisher<T> deleteById(R id) {
        return database.containsKey(id) ? Mono.just(database.remove(id)) : Mono.empty();
    }
}
