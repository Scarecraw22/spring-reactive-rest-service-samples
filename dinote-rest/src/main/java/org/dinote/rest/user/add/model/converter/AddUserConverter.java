package org.dinote.rest.user.add.model.converter;

import org.dinote.db.user.entity.User;
import org.dinote.rest.user.add.model.request.AddUserRequest;
import org.dinote.rest.user.add.model.response.AddUserResponse;

public class AddUserConverter {

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
