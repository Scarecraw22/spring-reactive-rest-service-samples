package org.dinote.rest.user.configuration;

import org.dinote.rest.user.add.helper.AddUserHelper;
import org.dinote.rest.user.add.model.converter.AddUserConverter;
import org.dinote.rest.user.handler.UserRequestHandler;
import org.dinote.rest.user.login.helper.LoginUserHelper;
import org.dinote.rest.user.login.model.converter.LoginUserConverter;
import org.dinote.service.user.UserReactiveService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRestConfiguration {

    @Bean
    public AddUserConverter addUserConverter() {
        return new AddUserConverter();
    }

    @Bean
    public LoginUserConverter loginUserConverter() {
        return new LoginUserConverter();
    }

    @Bean
    public AddUserHelper addUserHelper(UserReactiveService userReactiveService,
                                       AddUserConverter addUserConverter) {
        return new AddUserHelper(userReactiveService, addUserConverter);
    }

    @Bean
    public LoginUserHelper loginUserHelper(UserReactiveService userReactiveService,
                                           LoginUserConverter loginUserConverter) {
        return new LoginUserHelper(userReactiveService, loginUserConverter);
    }

    @Bean
    public UserRequestHandler userRequestHandler(AddUserHelper addUserHelper,
                                                 LoginUserHelper loginUserHelper) {
        return new UserRequestHandler(addUserHelper, loginUserHelper);
    }
}
