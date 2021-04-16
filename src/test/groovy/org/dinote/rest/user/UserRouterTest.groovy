package org.dinote.rest.user

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.dinote.db.initializers.DbIntegrationTestInitializer
import org.dinote.db.initializers.RedisIntegrationTestInitializer
import org.dinote.db.user.entity.User
import org.dinote.rest.user.request.AddUserRequest
import org.dinote.rest.user.response.AddUserResponse
import org.dinote.service.user.UserReactiveService
import org.junit.jupiter.api.extension.ExtendWith
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import spock.lang.Specification

import java.time.LocalDateTime

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
        initializers = [RedisIntegrationTestInitializer.class, DbIntegrationTestInitializer.class])
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
                .name("sample-name")
                .password("sample-password")
                .email("sample-email")
                .build()

        userReactiveService.addUser(_) >> Mono.just(User.builder()
                .id(1L)
                .name("sample-name")
                .password("sample-encoded-password")
                .email("sample-email")
                .createdOn(LocalDateTime.MIN)
                .updatedOn(LocalDateTime.MIN)
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
                            .id(1L)
                            .name("sample-name")
                            .email("sample-email")
                            .createdOn(LocalDateTime.MIN)
                            .updatedOn(LocalDateTime.MIN)
                            .build()
                })

    }
}
