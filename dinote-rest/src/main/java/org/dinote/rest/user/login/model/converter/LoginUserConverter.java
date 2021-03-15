package org.dinote.rest.user.login.model.converter;

import org.dinote.db.user.entity.User;
import org.dinote.messages.user.LoginMessage;
import org.dinote.rest.user.login.model.request.LoginUserRequest;
import org.dinote.rest.user.login.model.response.LoginUserResponse;

public class LoginUserConverter {

    public LoginMessage fromRequest(final LoginUserRequest request) {
        return LoginMessage.builder()
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
