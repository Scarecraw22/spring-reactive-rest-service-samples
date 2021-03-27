package org.dinote.db.user.dao;

import lombok.RequiredArgsConstructor;
import org.dinote.db.core.query.string.StringQueryBuilderFactory;
import org.dinote.db.user.converter.UserConverter;
import org.dinote.db.user.entity.User;
import org.dinote.db.user.validator.UserValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class UserR2dbcDao implements UserReactiveDao {

    private static final List<String> USER_COLUMNS = List.of("id", "name", "password", "email", "updated_on", "created_on");

    private final DatabaseClient client;
    private final UserConverter userConverter;
    private final Clock clock;
    private final StringQueryBuilderFactory queryFactory;
    private final UserValidator userValidator;

    @Override
    public Flux<User> findWithNameContains(@NotNull final String string) {
        return client.sql(queryFactory.create()
                                  .selectAll()
                                  .from("dinote.user")
                                  .where("LOWER(name) like :name")
                                  .build())
                .bind("name", "%" + string.toLowerCase() + "%")
                .map(userConverter)
                .all();
    }

    @Override
    public Mono<User> findByName(@NotNull final String name) {
        return client.sql(queryFactory.create()
                                  .selectAll()
                                  .from("dinote", "user")
                                  .where("name = :name")
                                  .build())
                .bind("name", name)
                .map(userConverter)
                .one();
    }

    @Override
    public Mono<User> save(@NotNull final User user) {
        userValidator.validateUserToAdd(user);
        return client.sql(queryFactory.create()
                                  .insertInto("dinote", "user", USER_COLUMNS)
                                  .values(List.of("default", ":name", ":password", ":email", ":updatedOn", ":createdOn"))
                                  .returningAll()
                                  .build())
                .bind("name", user.getName())
                .bind("password", user.getPassword())
                .bind("email", user.getEmail())
                .bind("updatedOn", LocalDateTime.now(clock))
                .bind("createdOn", LocalDateTime.now(clock))
                .map(userConverter)
                .one();
    }

    @Override
    public Mono<User> findById(@NotNull final Long id) {
        return client.sql(queryFactory.create()
                                  .selectAll()
                                  .from("dinote", "user")
                                  .where("id = :id")
                                  .build())
                .bind("id", id)
                .map(userConverter)
                .one();
    }

    @Override
    public Mono<User> deleteById(@NotNull final Long id) {
        return client.sql(queryFactory.create()
                                  .deleteFrom("dinote", "user")
                                  .where("id = :id")
                                  .returningAll()
                                  .build())
                .bind("id", id)
                .map(userConverter)
                .one();
    }

    @Override
    public Mono<User> findByEmail(@NotNull final String email) {
        return client.sql(queryFactory.create()
                                  .selectAll()
                                  .from("dinote", "user")
                                  .where("email = :email")
                                  .build())
                .bind("email", email)
                .map(userConverter)
                .one();
    }

    @Override
    public Mono<Boolean> existsByName(@NotNull final String name) {
        return Mono.from(findByName(name))
                .map(user -> user.getName().equals(name))
                .defaultIfEmpty(Boolean.FALSE);
    }
}
