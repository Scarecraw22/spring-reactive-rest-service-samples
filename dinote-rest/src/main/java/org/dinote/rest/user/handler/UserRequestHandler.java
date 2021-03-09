package org.dinote.rest.user.handler;

import lombok.RequiredArgsConstructor;
import org.dinote.core.validator.ObjectArgumentValidator;
import org.dinote.rest.user.add.helper.AddUserHelper;
import org.dinote.rest.user.add.model.request.AddUserRequest;
import org.dinote.rest.user.add.model.response.AddUserResponse;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserRequestHandler {

    private final AddUserHelper addUserHelper;

    public Mono<ServerResponse> handleAdd(final ServerRequest request) {
        ObjectArgumentValidator.requireNotNull(request);
        Publisher<AddUserResponse> publisher = addUserHelper.addUser(request.bodyToMono(AddUserRequest.class));

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(publisher, AddUserRequest.class);
    }
}
