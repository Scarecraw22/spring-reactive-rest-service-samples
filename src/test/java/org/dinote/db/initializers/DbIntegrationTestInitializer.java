package org.dinote.db.initializers;

import org.dinote.db.containers.PostgresqlTestContainer;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class DbIntegrationTestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        PostgresqlTestContainer postgresqlTestContainer = PostgresqlTestContainer.getInstance();
        postgresqlTestContainer.startWithStopOnShutdown();

        TestPropertyValues testPropertyValues = TestPropertyValues.of(
                "spring.r2dbc.url=" + postgresqlTestContainer.getJdbcUrl().replace("jdbc", "r2dbc"),
                "spring.r2dbc.username=" + postgresqlTestContainer.getUsername(),
                "spring.r2dbc.password=" + postgresqlTestContainer.getPassword(),
                "spring.jpa.show-sql=true"
        );

        testPropertyValues.and(defaultProperties());
        testPropertyValues.applyTo(applicationContext.getEnvironment());
    }

    public List<String> defaultProperties() {
        return List.of();
    }
}
