package org.dinote.db.user.dao;

import org.dinote.db.core.dao.AbstractInMemoryReactiveDao;
import org.dinote.db.user.entity.User;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class UserInMemoryReactiveDao extends AbstractInMemoryReactiveDao<User, Long> implements UserReactiveDao {

    private static final AtomicLong ACTUAL_ID = new AtomicLong(1L);

    @Override
    public Publisher<User> save(final User entity) {
        return Mono.from(findByName(entity.getName()))
                .flatMap(user -> Mono.error(new IllegalArgumentException("User with name: " + entity.getName() + " already exists")))
                .defaultIfEmpty(User.builder()
                                        .id(ACTUAL_ID.getAndIncrement())
                                        .name(entity.getName())
                                        .password(entity.getPassword())
                                        .email(entity.getEmail())
                                        .createdOn(LocalDateTime.now())
                                        .updatedOn(LocalDateTime.now())
                                        .build())
                .map(user -> {
                    User userToSave = (User) user;
                    database.put(userToSave.getId(), userToSave);
                    return userToSave;
                });
    }

    @Override
    public Publisher<User> findWithNameContains(final String string) {
        return findByPredicateOrEmpty(entry -> entry.getValue()
                .getName()
                .toLowerCase()
                .contains(string.toLowerCase())
        );
    }

    @Override
    public Publisher<User> findByName(final String name) {
        return findByPredicateOrEmpty(entry -> entry.getValue().getName().equals(name));
    }

    @Override
    public Publisher<User> findByEmail(final String email) {
        return findByPredicateOrEmpty(entry -> entry.getValue().getEmail().equals(email));
    }

    @Override
    public Publisher<Boolean> existsByName(final String name) {
        return Mono.from(findByName(name))
                .map(user -> user.getName().equals(name))
                .defaultIfEmpty(Boolean.FALSE);
    }
}
