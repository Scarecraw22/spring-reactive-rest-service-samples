package org.dinote.rest.user.add.helper;

import lombok.RequiredArgsConstructor;
import org.dinote.rest.user.add.model.converter.AddUserConverter;
import org.dinote.rest.user.add.model.request.AddUserRequest;
import org.dinote.rest.user.add.model.response.AddUserResponse;
import org.dinote.service.user.UserReactiveService;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AddUserHelper {

    private final UserReactiveService userReactiveService;
    private final AddUserConverter converter;

    public Publisher<AddUserResponse> addUser(Publisher<AddUserRequest> request) {
        return Mono.from(request)
                .map(converter::fromRequest)
                .flatMap(user -> Mono.from(userReactiveService.addUser(user)))
                .map(converter::toResponse);
    }
}
