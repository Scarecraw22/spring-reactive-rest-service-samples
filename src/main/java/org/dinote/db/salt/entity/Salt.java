package org.dinote.db.salt.entity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Salt {
    String hash;
}
