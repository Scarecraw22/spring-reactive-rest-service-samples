package org.dinote.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DinoteStringUtils {

    public boolean isNullOrBlank(final String string) {
        return DinoteObjectUtils.isNull(string) || string.isBlank();
    }

    public boolean isNullOrEmpty(final String string) {
        return DinoteObjectUtils.isNull(string) || string.isEmpty();
    }
}
