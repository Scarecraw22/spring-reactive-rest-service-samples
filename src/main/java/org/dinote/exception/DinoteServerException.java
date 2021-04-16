package org.dinote.exception;

import org.jetbrains.annotations.NotNull;

public class DinoteServerException extends RuntimeException {

    public DinoteServerException(@NotNull final String message) {
        super(message);
    }

    public DinoteServerException(@NotNull final String message,
                                 @NotNull final Throwable cause) {
        super(message, cause);
    }
}
