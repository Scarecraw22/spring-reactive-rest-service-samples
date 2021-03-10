package org.dinote.service.user;

import lombok.RequiredArgsConstructor;
import org.dinote.core.validator.ObjectArgumentValidator;
import org.dinote.core.validator.StringArgumentValidator;
import org.dinote.db.user.dao.UserReactiveDao;
import org.dinote.db.user.entity.User;
import org.dinote.db.user.validation.UserValidator;
import org.dinote.messages.user.LoginMessage;
import org.dinote.service.password.PasswordService;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserReactiveServiceImpl implements UserReactiveService {

    private final UserReactiveDao userReactiveDao;
    private final PasswordService passwordService;

    @Override
    public Publisher<User> addUser(final User user) {
        UserValidator.validateUserToAdd(user);
        return Mono.from(userReactiveDao.existsByName(user.getName()))
                .flatMap(aBoolean -> {
                    if (aBoolean) {
                        return Mono.empty();
                    }
                    return Mono.from(userReactiveDao.save(User.builder()
                                                                  .name(user.getName())
                                                                  .password(passwordService.encode(user.getPassword()))
                                                                  .email(user.getEmail())
                                                                  .build()));
                });
    }

    @Override
    public Publisher<User> findById(final Long id) {
        return userReactiveDao.findById(id);
    }

    @Override
    public Publisher<User> loginUser(final LoginMessage loginMessage) {
        ObjectArgumentValidator.requireNotNull(loginMessage);
        StringArgumentValidator.requireNotNullOrBlank(loginMessage.getLogin(), loginMessage.getPassword());
        return Mono.from(userReactiveDao.findByName(loginMessage.getLogin()))
                .switchIfEmpty(Mono.from(userReactiveDao.findByEmail(loginMessage.getLogin())))
                .flatMap(user -> {
                    if (user != null && passwordService.matches(loginMessage.getPassword(), user.getPassword())) {
                        return Mono.just(user);
                    } else {
                        return Mono.empty();
                    }
                });
    }
}
