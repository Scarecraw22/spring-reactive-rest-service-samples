package org.dinote.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DinoteObjectUtils {

    public boolean isNull(final Object object) {
        return object == null;
    }

    public boolean isNotNull(final Object object) {
        return !isNull(object);
    }
}
