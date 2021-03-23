package org.dinote.rest.user.handler;

import lombok.RequiredArgsConstructor;
import org.dinote.rest.user.converter.AddUserRequestConverter;
import org.dinote.rest.user.converter.LoginUserConverter;
import org.dinote.rest.user.request.AddUserRequest;
import org.dinote.rest.user.request.LoginUserRequest;
import org.dinote.service.user.UserReactiveService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserRequestHandler {

    private final UserReactiveService userReactiveService;
    private final AddUserRequestConverter addUserRequestConverter;
    private final LoginUserConverter loginUserConverter;

    public Mono<ServerResponse> handleAdd(final ServerRequest request) {
        return request.bodyToMono(AddUserRequest.class)
                .map(addUserRequestConverter::fromRequest)
                .flatMap(userReactiveService::addUser)
                .map(addUserRequestConverter::toResponse)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    public Mono<ServerResponse> handleLogin(final ServerRequest request) {
        return request.bodyToMono(LoginUserRequest.class)
                .map(loginUserConverter::fromRequest)
                .flatMap(userReactiveService::loginUser)
                .map(loginUserConverter::toResponse)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }
}
