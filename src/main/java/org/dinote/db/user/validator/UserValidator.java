package org.dinote.db.user.validator;

import org.dinote.db.user.entity.User;
import org.dinote.exception.DinoteServerException;
import org.dinote.utils.DinoteStringUtils;
import org.jetbrains.annotations.NotNull;

public class UserValidator {

    public void validateUserToAdd(@NotNull final User user) {
        if (DinoteStringUtils.isNullOrBlank(user.getName())) {
            throw new DinoteServerException("User cannot have null or blank name");
        }
        if (DinoteStringUtils.isNullOrBlank(user.getPassword())) {
            throw new DinoteServerException("User cannot have null or blank password");
        }
        if (DinoteStringUtils.isNullOrBlank(user.getEmail())) {
            throw new DinoteServerException("User cannot have null or blank email");
        }
    }
}
