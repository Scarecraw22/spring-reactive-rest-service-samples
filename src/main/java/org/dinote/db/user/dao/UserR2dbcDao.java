package org.dinote.db.user.dao;

import lombok.RequiredArgsConstructor;
import org.dinote.db.core.query.string.StringQueryBuilderFactory;
import org.dinote.db.user.converter.UserConverter;
import org.dinote.db.user.entity.User;
import org.dinote.db.user.validation.UserValidator;
import org.dinote.validator.StringArgumentValidator;
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

    @Override
    public Flux<User> findWithNameContains(final String string) {
        StringArgumentValidator.requireNotNullOrBlank(string);
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
    public Mono<User> findByName(final String name) {
        StringArgumentValidator.requireNotNullOrBlank(name);
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
    public Mono<User> save(final User user) {
        UserValidator.validateUserToAdd(user);
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
    public Mono<User> findById(final Long id) {
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
    public Mono<User> deleteById(final Long id) {
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
    public Mono<User> findByEmail(final String email) {
        StringArgumentValidator.requireNotNullOrBlank(email);
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
    public Mono<Boolean> existsByName(final String name) {
        return Mono.from(findByName(name))
                .map(user -> user.getName().equals(name))
                .defaultIfEmpty(Boolean.FALSE);
    }
}
