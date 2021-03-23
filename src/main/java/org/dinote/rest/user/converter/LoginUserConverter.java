package org.dinote.rest.user.converter;

import org.dinote.db.user.entity.User;
import org.dinote.rest.user.request.LoginUserRequest;
import org.dinote.rest.user.response.LoginUserResponse;
import org.dinote.service.messages.LoginUserMessage;
import org.springframework.stereotype.Component;

@Component
public class LoginUserConverter {

    public LoginUserMessage fromRequest(final LoginUserRequest request) {
        return LoginUserMessage.builder()
                .login(request.getLogin())
                .password(request.getPassword())
                .build();
    }

    public LoginUserResponse toResponse(final User user) {
        return LoginUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdOn(user.getCreatedOn())
                .updatedOn(user.getUpdatedOn())
                .build();
    }
}
