package org.dinote.service.user;

import lombok.RequiredArgsConstructor;
import org.dinote.db.user.dao.UserReactiveDao;
import org.dinote.db.user.entity.User;
import org.dinote.db.user.validation.UserValidator;
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
        return Mono.just(User.builder()
                                 .name(user.getName())
                                 .password(passwordService.encode(user.getPassword()))
                                 .email(user.getEmail())
                                 .build())
                .flatMap(mappedUser -> Mono.from(userReactiveDao.save(user)));
    }

    @Override
    public Publisher<User> findById(final Long id) {
        return userReactiveDao.findById(id);
    }
}
