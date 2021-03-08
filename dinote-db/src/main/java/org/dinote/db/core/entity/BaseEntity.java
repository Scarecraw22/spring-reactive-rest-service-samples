package org.dinote.db.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
public class BaseEntity {

    protected Long id;
    protected Date createdOn;
    protected Date updatedOn;
}
