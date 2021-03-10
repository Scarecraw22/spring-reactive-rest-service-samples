package org.dinote.service.user

import org.dinote.core.exception.DinoteServerException
import org.dinote.db.user.entity.User
import org.dinote.messages.user.LoginMessage
import org.dinote.service.password.PasswordService
import org.dinote.service.specification.ServiceSpecification
import org.dinote.utils.TestData
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Unroll

import java.time.Duration
import java.util.concurrent.atomic.AtomicLong

class UserReactiveServiceImplTest extends ServiceSpecification {

    private PasswordService passwordService = Mock()
    private UserReactiveService userReactiveService = new UserReactiveServiceImpl(userReactiveDao, passwordService)

    def "service should properly add service"() {
        given:
        passwordService.encode(TestData.STRING_3) >> TestData.STRING_1

        when:
        def result = userReactiveService.addUser(User.builder()
                .name(TestData.STRING_2)
                .password(TestData.STRING_3)
                .email(TestData.STRING_4)
                .build())

        then:
        AtomicLong id = new AtomicLong(-1L)
        StepVerifier.create(Mono.from(result).log())
                .assertNext(next -> {
                    assert next != null
                    assert next.getId() > 0
                    id.set(next.getId())
                    assert next.getName() == TestData.STRING_2
                    assert next.getEmail() == TestData.STRING_4
                    assert next.getPassword() == TestData.STRING_1
                    assert next.getCreatedOn() != null
                    assert next.getUpdatedOn() != null
                })
                .verifyComplete()

        cleanup:
        Mono.from(userReactiveDao.deleteById(id.get()))
                .log()
                .block(Duration.ofSeconds(1))
    }

    @Unroll
    def "#conditionString then should throw proper exception while adding user"() {
        when:
        userReactiveService.addUser(actualUser)

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

    @Unroll
    def "service should properly login user #methodMessage"() {
        given:
        String password = "sample-password"
        String encodedPassword = "sample-encoded-password"
        Mono.from(userReactiveDao.save(new User(actualName, encodedPassword, actualEmail)))
                .block(Duration.ofSeconds(1))
        passwordService.matches(password, encodedPassword) >> true

        when:
        def result = userReactiveService.loginUser(new LoginMessage(login, password))

        then:
        AtomicLong id = new AtomicLong(-1L)
        StepVerifier.create(Mono.from(result).log())
                .assertNext(next -> {
                    assert next != null
                    assert next.getId() > 0
                    id.set(next.getId())
                    assert next.getName() == actualName
                    assert next.getEmail() == actualEmail
                    assert next.getCreatedOn() != null
                    assert next.getUpdatedOn() != null
                })
                .verifyComplete()
        Mono.from(userReactiveDao.deleteById(id.get()))
                .log()
                .block(Duration.ofSeconds(1))

        where:
        actualName | actualEmail | login       | methodMessage
        "name-1"   | "email-1"   | actualName  | "by name"
        "name-2"   | "email-2"   | actualEmail | "by email"
    }

    def "service should properly return empty publisher when password is incorrect"() {
        given:
        String name = "sample-name-1"
        String password = "sample-password"
        String email = "sample-email"
        String encodedPassword = "sample-encoded-password"
        AtomicLong id = new AtomicLong(-1L)

        def savedUserId = Mono.from(userReactiveDao.save(new User(name, encodedPassword, email)))
                .block(Duration.ofSeconds(1)).getId()
        id.set(savedUserId)
        passwordService.matches(password, encodedPassword) >> false

        when:
        def result = userReactiveService.loginUser(new LoginMessage(name, password))

        then:
        StepVerifier.create(Mono.from(result).log())
                .expectNextCount(0)
                .verifyComplete()

        cleanup:
        Mono.from(userReactiveDao.deleteById(id.get()))
                .log()
                .block(Duration.ofSeconds(1))
    }

    def "service should properly return empty publisher when no user by name and email is found"() {
        given:
        String name = "sample-name-1"
        String password = "sample-password"

        when:
        def result = userReactiveService.loginUser(new LoginMessage(name, password))

        then:
        StepVerifier.create(Mono.from(result).log())
                .expectNextCount(0)
                .verifyComplete()
    }

    @Unroll
    def "#conditionString then should throw proper exception while finding user by email"() {
        when:
        userReactiveService.loginUser(loginMessage)

        then:
        thrown(DinoteServerException)

        where:
        loginMessage                       | conditionString
        null                               | "message is null"
        new LoginMessage(null, "password") | "message has null params"
        new LoginMessage("", "password")   | "message has empty params"
        new LoginMessage("username", " ")  | "message has blank params"
    }
}
