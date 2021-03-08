package org.dinote.db.user.validation;

import lombok.experimental.UtilityClass;
import org.dinote.core.exception.DinoteServerException;
import org.dinote.core.validator.StringArgumentValidator;
import org.dinote.db.user.entity.User;

@UtilityClass
public class UserValidator {

    public void validateNullUser(final User user) {
        if (user == null) {
            throw new DinoteServerException("Given object: [" + User.class.getName() + "] is null");
        }
    }

    public void validateUserToAdd(final User user) {
        validateNullUser(user);
        StringArgumentValidator.requireNotNullOrBlank(user.getName());
        StringArgumentValidator.requireNotNullOrBlank(user.getPassword());
        StringArgumentValidator.requireNotNullOrBlank(user.getEmail());
    }
}
