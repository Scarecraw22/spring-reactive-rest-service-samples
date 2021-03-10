package org.dinote.rest.user.login.helper;

import lombok.RequiredArgsConstructor;
import org.dinote.rest.user.login.model.converter.LoginUserConverter;
import org.dinote.rest.user.login.model.request.LoginUserRequest;
import org.dinote.rest.user.login.model.response.LoginUserResponse;
import org.dinote.service.user.UserReactiveService;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoginUserHelper {

    private final UserReactiveService userReactiveService;
    private final LoginUserConverter loginUserConverter;

    public Publisher<LoginUserResponse> loginUser(Publisher<LoginUserRequest> request) {
        return Mono.from(request)
                .map(loginUserConverter::fromRequest)
                .flatMap(req -> Mono.from(userReactiveService.loginUser(req)))
                .map(loginUserConverter::toResponse);
    }
}
