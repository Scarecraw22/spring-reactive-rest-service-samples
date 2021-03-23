package org.dinote.validator;

import lombok.experimental.UtilityClass;
import org.dinote.exception.DinoteServerException;

@UtilityClass
public class ObjectArgumentValidator {

    private static final String NULL_MSG = "Given object is null";

    public void requireNotNull(final Object object) {
        if (object == null) {
            throw new DinoteServerException(NULL_MSG, new NullPointerException(NULL_MSG));
        }
    }
}