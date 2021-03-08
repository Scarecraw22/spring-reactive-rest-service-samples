package org.dinote.service.user

import org.dinote.core.exception.DinoteServerException
import org.dinote.db.test.utils.TestDataUtil
import org.dinote.db.user.entity.User
import org.dinote.service.password.PasswordService
import org.dinote.service.specification.ServiceSpecification
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Unroll

import java.time.LocalDateTime

class UserReactiveServiceImplTest extends ServiceSpecification {

    private PasswordService passwordService = Mock()
    private UserReactiveService userReactiveService = new UserReactiveServiceImpl(userReactiveDao, passwordService)

    def "service should properly add service"() {
        given:
        passwordService.encode(_) >> TestDataUtil.ENCODED_PASSWORD_1

        when:
        def result = userReactiveService.addUser(TestDataUtil.USER_1)

        then:
        StepVerifier.create(Mono.from(result).log())
                .assertNext(next -> {
                    next != null
                    next.getId() > 0
                    next.getName() == TestDataUtil.USER_1.getName()
                    next.getEmail() == TestDataUtil.USER_1.getEmail()
                    next.getPassword() == TestDataUtil.ENCODED_PASSWORD_1
                    next.getCreatedOn().isBefore(LocalDateTime.now())
                    next.getUpdatedOn().isBefore(LocalDateTime.now())
                })
                .verifyComplete()
    }

    @Unroll
    def "#conditionString then should throw proper exception"() {
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
}
