package org.dinote.service.user

import org.dinote.db.test.utils.TestDataUtil
import org.dinote.service.password.PasswordService
import org.dinote.service.specification.ServiceSpecification
import reactor.test.StepVerifier

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
        StepVerifier.create(result.log())
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

}
