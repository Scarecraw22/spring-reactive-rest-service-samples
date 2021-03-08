package org.dinote.db.user.dao;

import org.dinote.db.core.dao.BasicReactiveDao;
import org.dinote.db.user.entity.User;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Responsible for aggregating operations on {@link User} entities in reactive way.
 *
 * @author Oskar Wąsikowski
 */
public interface UserReactiveDao extends BasicReactiveDao<User, Long> {

    Flux<User> findWithNameContains(@Param("string") final String string);

    Mono<User> findByName(@Param("name") final String name);
}
