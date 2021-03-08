package org.dinote.db.user.dao

import groovy.util.logging.Slf4j
import org.dinote.core.exception.DinoteServerException
import org.dinote.db.initializers.DbIntegrationTestInitializer
import org.dinote.db.specification.DbSpecification
import org.dinote.db.user.entity.User
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Unroll

import java.util.concurrent.atomic.AtomicLong

import static org.dinote.db.test.utils.TestDataUtil.USER_1
import static org.dinote.db.test.utils.TestDataUtil.USER_2

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = [DbIntegrationTestInitializer.class])
@ActiveProfiles(["test"])
class UserR2dbcDaoTest extends DbSpecification {

    @Autowired
    private UserReactiveDao userDao

    def "save and delete users"() {
        when: "users are saved"
        Mono<User> savedUser1 = userDao.save(USER_1)
        Mono<User> savedUser2 = userDao.save(USER_2)

        then: "check users and check their ids"
        Long id1 = assertThatUserIsSavedAndReturnId(savedUser1, USER_1)
        Long id2 = assertThatUserIsSavedAndReturnId(savedUser2, USER_2)
        id1 != id2
        id1 + 1 == id2

        when: "retrieve users from db by id to verify if they are persisted"
        Mono<User> userFromDb1 = userDao.findById(id1)
        Mono<User> userFromDb2 = userDao.findById(id2)

        then: "check users retrieved from db"
        assertUser(userFromDb1, USER_1)
        assertUser(userFromDb2, USER_2)

        when: "delete users"
        Mono<User> removedUser1 = userDao.deleteById(id1)
        Mono<User> removedUser2 = userDao.deleteById(id2)

        then: "check if returned users are correct"
        assertUser(removedUser1, USER_1)
        assertUser(removedUser2, USER_2)

        when: "retrieve users from db by id"
        Mono<User> emptyUser1 = userDao.findById(id1)
        Mono<User> emptyUser2 = userDao.findById(id2)

        then: "users shouldn't be in db so nothing is returned"
        StepVerifier.create(emptyUser1.log())
                .expectNextCount(0)
                .verifyComplete()
        StepVerifier.create(emptyUser2.log())
                .expectNextCount(0)
                .verifyComplete()
    }

    def "delete by id user that not exists should return empty publisher"() {
        when:
        def result = userDao.deleteById(100L)

        then:
        StepVerifier.create(Mono.from(result).log())
                .expectNextCount(0)
                .verifyComplete()
    }

    def "find not existing user by id should return empty publisher"() {
        when:
        def result = userDao.findById(100L)

        then:
        StepVerifier.create(Mono.from(result).log())
                .expectNextCount(0)
                .verifyComplete()
    }

    def "find not existing user by name should return empty publisher"() {
        when:
        def result = userDao.findByName("name-that-not-exists")

        then:
        StepVerifier.create(Mono.from(result).log())
                .expectNextCount(0)
                .verifyComplete()
    }

    @Unroll
    def "#conditionString then should throw proper exception"() {
        when:
        userDao.save(actualUser)

        then:
        thrown(DinoteServerException)

        where:
        actualUser                           | conditionString
        null                                 | "user is null"
        new User(null, null, null)           | "user has null params"
        new User(null, "param 1", "param 2") | "user has one null params"
        new User("param1", "param2", "")     | "user has one empty params"
        new User("param1", "   ", "param3")  | "user has one blank params"
    }

    private static void assertUser(Mono<User> userMono,
                                   User expectedUser) {
        StepVerifier.create(userMono.log())
                .assertNext(user -> {
                    checkUser(user, expectedUser)
                })
                .verifyComplete()
    }

    private static Long assertThatUserIsSavedAndReturnId(Mono<User> userMono,
                                                         User expectedUser) {
        AtomicLong id = new AtomicLong()
        StepVerifier.create(userMono.log())
                .assertNext(user -> {
                    id.set(user.getId())
                    checkUser(user, expectedUser)
                })
                .verifyComplete()
        return id.get()
    }

    private static void checkUser(User actualUser, User expectedUser) {
        assert actualUser != null
        assert actualUser.getId() > 0L
        assert actualUser.getPassword() != null && !actualUser.getPassword().isBlank()
        assert actualUser.getCreatedOn() != null
        assert actualUser.getUpdatedOn() != null
        assert actualUser.getName() == expectedUser.getName()
        assert actualUser.getEmail() == expectedUser.getEmail()
    }
}
