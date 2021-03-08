package org.dinote.utils;

import lombok.experimental.UtilityClass;

import static org.dinote.utils.DinoteObjectUtils.isNull;

@UtilityClass
public class DinoteStringUtils {

    public boolean isNullOrBlank(final String string) {
        return isNull(string) || string.isBlank();
    }

    public boolean isNullOrEmpty(final String string) {
        return isNull(string) || string.isEmpty();
    }
}
