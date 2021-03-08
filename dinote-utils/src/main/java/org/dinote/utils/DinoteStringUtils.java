package org.dinote.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DinoteStringUtils {

    public boolean isNullOrBlank(String string) {
        return string == null || string.isBlank();
    }

    public boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
