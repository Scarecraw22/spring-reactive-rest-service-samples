package org.dinote.service.user;

import lombok.RequiredArgsConstructor;
import org.dinote.db.user.dao.UserReactiveDao;
import org.dinote.db.user.entity.User;
import org.dinote.db.user.validation.UserValidator;
import org.dinote.service.messages.LoginUserMessage;
import org.dinote.service.password.PasswordService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserReactiveServiceImpl implements UserReactiveService {

    private final UserReactiveDao userReactiveDao;
    private final PasswordService passwordService;

    @Override
    public Mono<User> addUser(final User user) {
        UserValidator.validateUserToAdd(user);
        return Mono.from(userReactiveDao.existsByName(user.getName()))
                .flatMap(aBoolean -> {
                    if (aBoolean) {
                        return Mono.empty();
                    }
                    return userReactiveDao.save(User.builder()
                                                        .name(user.getName())
                                                        .password(passwordService.encode(user.getPassword()))
                                                        .email(user.getEmail())
                                                        .build());
                });
    }

    @Override
    public Mono<User> findById(final Long id) {
        return userReactiveDao.findById(id);
    }

    @Override
    public Mono<User> loginUser(final LoginUserMessage message) {
        return Mono.from(userReactiveDao.findByName(message.getLogin()))
                .switchIfEmpty(Mono.from(userReactiveDao.findByEmail(message.getLogin())))
                .flatMap(user -> {
                    if (user != null && passwordService.matches(message.getPassword(), user.getPassword())) {
                        return Mono.just(user);
                    } else {
                        return Mono.empty();
                    }
                });
    }
}
