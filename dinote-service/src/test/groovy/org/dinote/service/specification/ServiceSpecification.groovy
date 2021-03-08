package org.dinote.service.specification

import org.dinote.db.salt.dao.SaltInMemoryReactiveDao
import org.dinote.db.salt.dao.SaltReactiveDao
import org.dinote.db.user.dao.UserInMemoryReactiveDao
import org.dinote.db.user.dao.UserReactiveDao
import spock.lang.Specification

abstract class ServiceSpecification extends Specification {

    protected SaltReactiveDao saltReactiveDao = new SaltInMemoryReactiveDao()
    protected UserReactiveDao userReactiveDao = new UserInMemoryReactiveDao()
}
