package org.dinote.rest.user.login.model.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.dinote.rest.model.request.DinoteRestRequest;

@Value
@Builder
@Jacksonized
public class LoginUserRequest implements DinoteRestRequest {
    String login;
    String password;
}
