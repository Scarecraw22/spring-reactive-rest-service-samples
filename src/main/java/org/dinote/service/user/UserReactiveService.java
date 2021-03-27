package org.dinote.service.user;

import org.dinote.db.user.entity.User;
import org.dinote.service.messages.LoginUserMessage;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

public interface UserReactiveService {

    Mono<User> addUser(@NotNull User user);

    Mono<User> findById(@NotNull Long id);

    Mono<User> loginUser(@NotNull LoginUserMessage message);
}
