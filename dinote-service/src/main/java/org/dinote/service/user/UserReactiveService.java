package org.dinote.service.user;

import org.dinote.db.user.entity.User;
import org.reactivestreams.Publisher;

public interface UserReactiveService {

    Publisher<User> addUser(User user);
}
