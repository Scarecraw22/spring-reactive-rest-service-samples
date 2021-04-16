package org.dinote.db.user.dao

import groovy.util.logging.Slf4j
import org.dinote.db.initializers.DbIntegrationTestInitializer
import org.dinote.db.specification.DbSpecification
import org.dinote.db.user.entity.User
import org.dinote.exception.DinoteServerException
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Unroll

import java.time.Duration
import java.util.concurrent.atomic.AtomicLong

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = [DbIntegrationTestInitializer.class])
class UserR2dbcDaoTest extends DbSpecification {

    @Autowired
    private UserReactiveDao userDao

    def "save and delete users"() {
        given:
        User user1 = User.builder()
                .name("name-1")
                .password("password-1")
                .email("email-1")
                .build()
        User user2 = User.builder()
                .name("name-2")
                .password("password-2")
                .email("email-2")
                .build()

        when: "users are saved"
        Mono<User> savedUser1 = userDao.save(user1)
        Mono<User> savedUser2 = userDao.save(user2)

        then: "check users and check their ids"
        Long id1 = assertThatUserIsSavedAndReturnId(savedUser1, user1)
        Long id2 = assertThatUserIsSavedAndReturnId(savedUser2, user2)
        id1 != id2
        id1 + 1 == id2

        when: "retrieve users from db by id to verify if they are persisted"
        Mono<User> userFromDb1 = userDao.findById(id1)
        Mono<User> userFromDb2 = userDao.findById(id2)

        then: "check users retrieved from db"
        assertUser(userFromDb1, user1)
        assertUser(userFromDb2, user2)

        when: "delete users"
        Mono<User> removedUser1 = userDao.deleteById(id1)
        Mono<User> removedUser2 = userDao.deleteById(id2)

        then: "check if returned users are correct"
        assertUser(removedUser1, user1)
        assertUser(removedUser2, user2)

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
        StepVerifier.create(result.log())
                .expectNextCount(0)
                .verifyComplete()
    }

    def "find not existing user by id should return empty publisher"() {
        when:
        def result = userDao.findById(100L)

        then:
        StepVerifier.create(result.log())
                .expectNextCount(0)
                .verifyComplete()
    }

    def "find not existing user by name should return empty publisher"() {
        when:
        def result = userDao.findByName("name-that-not-exists")

        then:
        StepVerifier.create(result.log())
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
        new User(null, null, null)           | "user has null params"
        new User(null, "param 1", "param 2") | "user has one null params"
        new User("param1", "param2", "")     | "user has one empty params"
        new User("param1", "   ", "param3")  | "user has one blank params"
    }

    def "findByEmail method should properly find user by it's email"() {
        given:
        def name = "name"
        def password = "password"
        def email = "email"
        User user = User.builder()
                .name(name)
                .password(password)
                .email(email)
                .build()

        when:
        def saved = userDao.save(user)

        then:
        StepVerifier.create(saved.log())
                .expectNextCount(1)
                .verifyComplete()

        when:
        def foundByEmail = userDao.findByEmail(email)

        then:
        AtomicLong id = new AtomicLong()
        StepVerifier.create(Mono.from(foundByEmail).log())
                .assertNext(next -> {
                    assert next != null
                    id.set(next.getId())
                    assert next.getId() > 0
                    assert next.name == name
                    assert next.password == password
                    assert next.email == email
                    assert next.createdOn != null
                    assert next.updatedOn != null
                })
                .verifyComplete()

        cleanup:
        userDao.deleteById(id.get())
                .log()
                .block(Duration.ofSeconds(1))
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
