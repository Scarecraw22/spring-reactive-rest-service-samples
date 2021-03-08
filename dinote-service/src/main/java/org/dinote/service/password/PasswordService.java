package org.dinote.service.password;

public interface PasswordService {

    String encode(final String rawPassword);

    boolean matches(final String rawPassword,
                    final String encodedPassword);
}
