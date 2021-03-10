package org.dinote.rest.user.handler;

import lombok.RequiredArgsConstructor;
import org.dinote.core.validator.ObjectArgumentValidator;
import org.dinote.rest.user.add.helper.AddUserHelper;
import org.dinote.rest.user.add.model.request.AddUserRequest;
import org.dinote.rest.user.add.model.response.AddUserResponse;
import org.dinote.rest.user.login.helper.LoginUserHelper;
import org.dinote.rest.user.login.model.request.LoginUserRequest;
import org.dinote.rest.user.login.model.response.LoginUserResponse;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserRequestHandler {

    private final AddUserHelper addUserHelper;
    private final LoginUserHelper loginUserHelper;

    public Mono<ServerResponse> handleAdd(final ServerRequest request) {
        ObjectArgumentValidator.requireNotNull(request);
        Publisher<AddUserResponse> publisher = addUserHelper.addUser(request.bodyToMono(AddUserRequest.class));

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(publisher, AddUserRequest.class);
    }

    public Mono<ServerResponse> handleLogin(final ServerRequest request) {
        ObjectArgumentValidator.requireNotNull(request);
        Publisher<LoginUserResponse> publisher = loginUserHelper.loginUser(request.bodyToMono(LoginUserRequest.class));

        return Mono.from(publisher)
                .flatMap(response -> ServerResponse.accepted()
                        .bodyValue(response))
                .switchIfEmpty(ServerResponse.status(409)
                                       .body(BodyInserters.empty()));
    }
}
