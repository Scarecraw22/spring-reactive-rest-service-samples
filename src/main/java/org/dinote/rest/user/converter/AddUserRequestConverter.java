package org.dinote.rest.user.converter;

import org.dinote.db.user.entity.User;
import org.dinote.rest.user.request.AddUserRequest;
import org.dinote.rest.user.response.AddUserResponse;
import org.springframework.stereotype.Component;

@Component
public class AddUserRequestConverter {

    public User fromRequest(final AddUserRequest request) {
        return User.builder()
                .name(request.getName())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();
    }

    public AddUserResponse toResponse(final User model) {
        return AddUserResponse.builder()
                .id(model.getId())
                .name(model.getName())
                .email(model.getEmail())
                .createdOn(model.getCreatedOn())
                .updatedOn(model.getUpdatedOn())
                .build();
    }
}
