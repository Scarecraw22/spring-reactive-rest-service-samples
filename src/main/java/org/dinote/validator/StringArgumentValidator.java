package org.dinote.validator;

import lombok.experimental.UtilityClass;
import org.dinote.exception.DinoteServerException;

import java.util.Arrays;

@UtilityClass
public class StringArgumentValidator {

    public void requireNotNullOrEmpty(final String... strings) {
        Arrays.asList(strings).forEach(string -> {
            ObjectArgumentValidator.requireNotNull(string);
            if (string.isBlank()) {
                throw new DinoteServerException("Given string is empty");
            }
        });
    }

    public void requireNotNullOrBlank(final String... strings) {
        Arrays.asList(strings).forEach(string -> {
            ObjectArgumentValidator.requireNotNull(string);
            if (string.isBlank()) {
                throw new DinoteServerException("Given string is blank");
            }
        });
    }
}
