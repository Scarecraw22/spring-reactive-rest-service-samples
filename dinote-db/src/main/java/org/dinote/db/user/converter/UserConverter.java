package org.dinote.db.user.converter;

import io.r2dbc.spi.Row;
import org.dinote.db.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class UserConverter implements Function<Row, User> {
    @Override
    public User apply(Row row) {
        return User.builder()
                .id(row.get("id", Long.class))
                .password(row.get("password", String.class))
                .name(row.get("name", String.class))
                .email(row.get("email", String.class))
                .updatedOn(row.get("updated_on", Date.class))
                .createdOn(row.get("created_on", Date.class))
                .build();
    }
}
