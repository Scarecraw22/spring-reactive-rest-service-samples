package org.dinote.db.user.dao;

import org.dinote.db.core.dao.AbstractInMemoryReactiveDao;
import org.dinote.db.user.entity.User;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class UserInMemoryReactiveDao extends AbstractInMemoryReactiveDao<User, Long> implements UserReactiveDao {

    private Long actualId = 1L;

    @Override
    public Publisher<User> save(User entity) {
        return Mono.from(findByName(entity.getName()))
                .flatMap(user -> Mono.error(new IllegalArgumentException("User with name: " + entity.getName() + " already exists")))
                .defaultIfEmpty(User.builder()
                                        .id(actualId++)
                                        .name(entity.getName())
                                        .password(entity.getPassword())
                                        .email(entity.getEmail())
                                        .createdOn(LocalDateTime.now())
                                        .updatedOn(LocalDateTime.now())
                                        .build())
                .map(user -> {
                    User userToSave = (User) user;
                    database.put(actualId, userToSave);
                    return userToSave;
                });
    }

    @Override
    public Publisher<User> findWithNameContains(String string) {
        return database.entrySet().stream()
                .filter(entry -> entry.getValue().getName().toLowerCase().contains(string.toLowerCase()))
                .findFirst()
                .map(data -> Mono.just(data.getValue()))
                .orElse(Mono.empty());
    }

    @Override
    public Publisher<User> findByName(String name) {
        return database.entrySet().stream()
                .filter(entry -> entry.getValue().getName().equals(name))
                .findFirst()
                .map(entry -> Mono.just(entry.getValue()))
                .orElse(Mono.empty());
    }
}
