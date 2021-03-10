package org.dinote.db.core.dao;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

abstract public class AbstractInMemoryReactiveDao<T, R> implements BasicReactiveDao<T, R> {

    protected final Map<R, T> database;

    protected AbstractInMemoryReactiveDao() {
        this.database = new HashMap<>();
    }

    @Override
    public Publisher<T> findById(final R id) {
        return database.containsKey(id) ? Mono.just(database.get(id)) : Mono.empty();
    }

    @Override
    public Publisher<T> deleteById(final R id) {
        return database.containsKey(id) ? Mono.just(database.remove(id)) : Mono.empty();
    }

    protected Publisher<T> findByPredicateOrEmpty(final Predicate<Map.Entry<R, T>> predicate) {
        return database.entrySet().stream()
                .filter(predicate)
                .findFirst()
                .map(entry -> Mono.just(entry.getValue()))
                .orElse(Mono.empty());
    }
}
