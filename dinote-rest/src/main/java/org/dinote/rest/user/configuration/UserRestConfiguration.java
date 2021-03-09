package org.dinote.rest.user.configuration;

import org.dinote.rest.user.add.helper.AddUserHelper;
import org.dinote.rest.user.add.model.converter.AddUserConverter;
import org.dinote.rest.user.add.model.converter.AddUserConverterImpl;
import org.dinote.rest.user.handler.UserRequestHandler;
import org.dinote.service.user.UserReactiveService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRestConfiguration {

    @Bean
    public AddUserConverter addUserConverter() {
        return new AddUserConverterImpl();
    }

    @Bean
    public AddUserHelper addUserHelper(UserReactiveService userReactiveService,
                                       AddUserConverter addUserConverter) {
        return new AddUserHelper(userReactiveService, addUserConverter);
    }

    @Bean
    public UserRequestHandler userRequestHandler(AddUserHelper addUserHelper) {
        return new UserRequestHandler(addUserHelper);
    }
}
