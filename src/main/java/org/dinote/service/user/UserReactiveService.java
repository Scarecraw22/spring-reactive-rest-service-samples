package org.dinote.service.user;

import org.dinote.db.user.entity.User;
import org.dinote.service.messages.LoginUserMessage;
import reactor.core.publisher.Mono;

public interface UserReactiveService {

    Mono<User> addUser(User user);

    Mono<User> findById(Long id);

    Mono<User> loginUser(LoginUserMessage message);
}
