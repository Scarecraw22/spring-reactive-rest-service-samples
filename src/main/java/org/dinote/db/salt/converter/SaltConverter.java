package org.dinote.db.salt.converter;

import io.r2dbc.spi.Row;
import org.dinote.db.salt.entity.Salt;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class SaltConverter implements Function<Row, Salt> {
    @Override
    public Salt apply(final Row row) {
        return Salt.builder()
                .hash(row.get("hash", String.class))
                .build();
    }
}
