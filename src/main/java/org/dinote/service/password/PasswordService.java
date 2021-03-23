package org.dinote.service.password;

/**
 * Responsible for managing password
 *
 * @author Oskar Wąsikowski
 */
public interface PasswordService {

    String encode(final String rawPassword);

    boolean matches(final String rawPassword,
                    final String encodedPassword);
}
