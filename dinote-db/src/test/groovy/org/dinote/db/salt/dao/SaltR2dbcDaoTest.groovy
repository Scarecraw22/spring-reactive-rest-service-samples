package org.dinote.db.salt.dao

import groovy.util.logging.Slf4j
import org.dinote.db.initializers.DbIntegrationTestInitializer
import org.dinote.db.salt.entity.Salt
import org.dinote.db.specification.DbSpecification
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = [DbIntegrationTestInitializer.class])
@ActiveProfiles(["test"])
class SaltR2dbcDaoTest extends DbSpecification {

    @Autowired
    private SaltReactiveDao saltReactiveDao

    def "getSalt should properly return salt from db"() {
        when:
        Mono<Salt> salt = saltReactiveDao.getSalt()

        then:
        StepVerifier.create(salt.log())
                .assertNext(s -> s != null)
    }
}
