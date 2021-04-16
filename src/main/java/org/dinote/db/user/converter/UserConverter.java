package org.dinote.db.user.converter;

import io.r2dbc.spi.Row;
import org.dinote.db.user.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Function;

@Component
public class UserConverter implements Function<Row, User> {
    @Override
    public User apply(final Row row) {
        return User.builder()
                .id(row.get("id", Long.class))
                .password(row.get("password", String.class))
                .name(row.get("name", String.class))
                .email(row.get("email", String.class))
                .updatedOn(row.get("updated_on", LocalDateTime.class))
                .createdOn(row.get("created_on", LocalDateTime.class))
                .build();
    }
}
