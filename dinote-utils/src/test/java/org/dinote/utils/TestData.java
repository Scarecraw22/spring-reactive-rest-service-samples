package org.dinote.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class TestData {

    public Long LONG_1 = 1L;
    public Long LONG_2 = 2L;
    public Long LONG_3 = 3L;

    public String STRING_1 = "sample-string-1";
    public String STRING_2 = "sample-string-2";
    public String STRING_3 = "sample-string-3";

    public LocalDateTime LOCAL_DATE_TIME_1 = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
    public LocalDateTime LOCAL_DATE_TIME_2 = LocalDateTime.of(2021, 1, 2, 0, 0, 0);
}
