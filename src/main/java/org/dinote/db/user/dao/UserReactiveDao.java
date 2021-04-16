package org.dinote.db.user.dao;

import org.dinote.db.core.dao.BasicReactiveDao;
import org.dinote.db.user.entity.User;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Responsible for aggregating operations on {@link User} entities in reactive way.
 *
 * @author Oskar Wąsikowski
 */
public interface UserReactiveDao extends BasicReactiveDao<User, Long> {

    Flux<User> findWithNameContains(@NotNull final String string);

    Mono<User> findByName(@NotNull final String name);

    Mono<User> findByEmail(@NotNull final String email);

    Mono<Boolean> existsByName(@NotNull final String name);
}
