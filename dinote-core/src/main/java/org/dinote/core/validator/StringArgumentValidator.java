package org.dinote.core.validator;

import lombok.experimental.UtilityClass;
import org.dinote.core.exception.DinoteServerException;

@UtilityClass
public class StringArgumentValidator {

    public void requireNotNullOrEmpty(final String string) {
        ObjectArgumentValidator.requireNotNull(string);
        if (string.isEmpty()) {
            throw new DinoteServerException("Given string is empty");
        }
    }

    public void requireNotNullOrBlank(final String string) {
        ObjectArgumentValidator.requireNotNull(string);
        if (string.isBlank()) {
            throw new DinoteServerException("Given string is blank");
        }
    }
}