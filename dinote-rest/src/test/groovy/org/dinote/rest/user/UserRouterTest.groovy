package org.dinote.rest.user

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.dinote.db.user.entity.User
import org.dinote.rest.TestApplication
import org.dinote.rest.initializers.RedisIntegrationTestInitializer
import org.dinote.rest.user.add.model.request.AddUserRequest
import org.dinote.rest.user.add.model.response.AddUserResponse
import org.dinote.service.user.UserReactiveService
import org.dinote.utils.TestData
import org.junit.jupiter.api.extension.ExtendWith
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import spock.lang.Specification

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = ["spring.main.allow-bean-definition-overriding=true"])
@ContextConfiguration(initializers = [RedisIntegrationTestInitializer.class], classes = [TestApplication.class])
@ActiveProfiles(value = ["rest-test"])
class UserRouterTest extends Specification {

    @Autowired
    private WebTestClient webTestClient

    @Autowired
    private ObjectMapper objectMapper

    @SpringBean
    private UserReactiveService userReactiveService = Mock()

    def "Add user test"() {
        given:
        AddUserRequest addUserRequest = AddUserRequest.builder()
                .name(TestData.STRING_1)
                .password(TestData.STRING_2)
                .email(TestData.STRING_3)
                .build()

        userReactiveService.addUser(_) >> Mono.just(User.builder()
                .id(TestData.LONG_1)
                .name(TestData.STRING_1)
                .password(TestData.STRING_2)
                .email(TestData.STRING_3)
                .createdOn(TestData.LOCAL_DATE_TIME_1)
                .updatedOn(TestData.LOCAL_DATE_TIME_2)
                .build()).log()

        expect:
        webTestClient
                .post()
                .uri("/users/user")
                .bodyValue(addUserRequest)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(result -> {
                    AddUserResponse responseBody = objectMapper.readValue(result.getResponseBody(), AddUserResponse.class)
                    log.debug("Response body: {}", responseBody)
                    responseBody == AddUserResponse.builder()
                            .id(TestData.LONG_1)
                            .name(TestData.STRING_2)
                            .email(TestData.STRING_3)
                            .createdOn(TestData.LOCAL_DATE_TIME_1)
                            .updatedOn(TestData.LOCAL_DATE_TIME_2)
                            .build()
                })

    }
}
