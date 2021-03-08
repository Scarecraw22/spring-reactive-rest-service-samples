package org.dinote.db.salt.dao;

import org.dinote.db.salt.entity.Salt;
import org.reactivestreams.Publisher;

public interface SaltReactiveDao {

    Publisher<Salt> getSalt();
}
