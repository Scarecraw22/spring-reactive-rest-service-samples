package org.dinote.rest.user.add.model.converter;

import org.dinote.db.user.entity.User;
import org.dinote.rest.model.converter.RestModelConverter;
import org.dinote.rest.user.add.model.request.AddUserRequest;
import org.dinote.rest.user.add.model.response.AddUserResponse;

public interface AddUserConverter extends RestModelConverter<AddUserRequest, User, AddUserResponse> {
}
