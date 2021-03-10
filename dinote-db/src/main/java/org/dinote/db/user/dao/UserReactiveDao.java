package org.dinote.db.user.dao;

import org.dinote.db.core.dao.BasicReactiveDao;
import org.dinote.db.user.entity.User;
import org.reactivestreams.Publisher;

/**
 * Responsible for aggregating operations on {@link User} entities in reactive way.
 *
 * @author Oskar Wąsikowski
 */
public interface UserReactiveDao extends BasicReactiveDao<User, Long> {

    Publisher<User> findWithNameContains(final String string);

    Publisher<User> findByName(final String name);

    Publisher<User> findByEmail(final String email);

    Publisher<Boolean> existsByName(final String name);
}
