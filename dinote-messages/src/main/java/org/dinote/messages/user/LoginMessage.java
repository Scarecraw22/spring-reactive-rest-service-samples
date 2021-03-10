package org.dinote.messages.user;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class LoginMessage {

    String login;
    String password;
}
