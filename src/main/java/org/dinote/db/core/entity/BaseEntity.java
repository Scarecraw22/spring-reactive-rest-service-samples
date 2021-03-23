package org.dinote.db.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
public class BaseEntity {

    protected Long id;
    protected LocalDateTime createdOn;
    protected LocalDateTime updatedOn;
}
