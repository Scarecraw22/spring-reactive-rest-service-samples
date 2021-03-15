package org.dinote.rest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@SpringBootApplication(exclude = { R2dbcAutoConfiguration.class })
public class TestApplication {

    @Primary
    @Bean
    @Profile(value = "rest-test")
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity.authorizeExchange()
                .pathMatchers(HttpMethod.POST, "/users/user").permitAll()
                .anyExchange().authenticated()
                .and().csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }
}
