package org.dinote.db.utils;

import lombok.experimental.UtilityClass;
import org.dinote.db.user.entity.User;

import java.time.LocalDateTime;

@UtilityClass
public class TestDataUtil {

    public final Long ID_1 = 1L;
    public final Long ID_2 = 2L;
    public final Long ID_3 = 3L;
    public final String NAME_1 = "sample-name-1";
    public final String NAME_2 = "sample-name-2";
    public final String NAME_3 = "sample-name-3";
    public final String PASSWORD_1 = "sample-password-1";
    public final String PASSWORD_2 = "sample-password-2";
    public final String PASSWORD_3 = "sample-password-3";
    public final String ENCODED_PASSWORD_1 = "sample-encoded-password-1";
    public final String ENCODED_PASSWORD_2 = "sample-encoded-password-2";
    public final String ENCODED_PASSWORD_3 = "sample-encoded-password-3";
    public final String EMAIL_1 = "sample-email-1";
    public final String EMAIL_2 = "sample-email-2";
    public final String EMAIL_3 = "sample-email-3";
    public final LocalDateTime DATE_1 = LocalDateTime.now();
    public final LocalDateTime DATE_2 = LocalDateTime.now();
    public final LocalDateTime DATE_3 = LocalDateTime.now();

    public final User USER_1 = createUser(ID_1, NAME_1, ENCODED_PASSWORD_1, EMAIL_1, DATE_1, DATE_1);
    public final User USER_2 = createUser(ID_2, NAME_2, ENCODED_PASSWORD_2, EMAIL_2, DATE_2, DATE_2);
    public final User USER_3 = createUser(ID_3, NAME_3, ENCODED_PASSWORD_3, EMAIL_3, DATE_3, DATE_3);

    private User createUser(Long id,
                            String name,
                            String password,
                            String email,
                            LocalDateTime createdOn,
                            LocalDateTime updatedOn) {

        return User.builder()
                .id(id)
                .name(name)
                .password(password)
                .email(email)
                .createdOn(createdOn)
                .updatedOn(updatedOn)
                .build();
    }
}
