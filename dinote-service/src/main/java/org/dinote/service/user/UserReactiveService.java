package org.dinote.service.user;

import org.dinote.db.user.entity.User;
import org.dinote.messages.user.LoginMessage;
import org.reactivestreams.Publisher;

public interface UserReactiveService {

    Publisher<User> addUser(User user);

    Publisher<User> findById(Long id);

    Publisher<User> loginUser(LoginMessage loginMessage);
}
