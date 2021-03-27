package org.dinote.service.password;

import org.jetbrains.annotations.NotNull;

/**
 * Responsible for managing password
 *
 * @author Oskar Wąsikowski
 */
public interface PasswordService {

    String encode(@NotNull final String rawPassword);

    boolean matches(@NotNull final String rawPassword,
                    @NotNull final String encodedPassword);
}
