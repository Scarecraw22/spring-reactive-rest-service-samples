package org.dinote.rest.user.add.model.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.dinote.rest.model.request.DinoteRestRequest;

@Value
@Builder
@Jacksonized
public class AddUserRequest implements DinoteRestRequest {

    String name;
    String password;
    String email;
}
