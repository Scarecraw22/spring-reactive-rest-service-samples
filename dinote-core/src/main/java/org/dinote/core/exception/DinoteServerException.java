package org.dinote.core.exception;

public class DinoteServerException extends RuntimeException {

    public DinoteServerException(final String message) {
        super(message);
    }

    public DinoteServerException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
