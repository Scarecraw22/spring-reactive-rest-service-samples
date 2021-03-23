package org.dinote.service.messages;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginUserMessage {

    String login;
    String password;
}
