package org.dinote.db.user.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import org.dinote.db.core.entity.BaseEntity;

@Value
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = { "password" })
@ToString(callSuper = true, exclude = { "password" })
public class User extends BaseEntity {

    String name;
    String password;
    String email;
}
