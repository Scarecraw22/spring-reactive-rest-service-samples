package org.dinote.rest.user.router;

import org.dinote.rest.user.handler.UserRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class UserRouter {

    private static final String USERS_PATH = "/users";
    private static final String USER_PATH = USERS_PATH + "/user";
    private static final String LOGIN_USER_PATH = USERS_PATH + "/login";

    @Bean
    public RouterFunction<ServerResponse> route(UserRequestHandler userRequestHandler) {
        return RouterFunctions.route(POST(USER_PATH).and(accept(MediaType.APPLICATION_JSON)), userRequestHandler::handleAdd)
                .andRoute(POST(LOGIN_USER_PATH).and(accept(MediaType.APPLICATION_JSON)), userRequestHandler::handleLogin);
    }
}
