package com.hugo.itireland;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Testcontainers
public abstract class AbstractTestContainers {
    @Container
    protected static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres")
                    .withDatabaseName("ITIreland-testcontainer")
                    .withUsername("itireland")
                    .withPassword("123456");

    @DynamicPropertySource
    protected static void registerDataSourceProperties(
            DynamicPropertyRegistry registry) {
        registry.add(
                "spring.datasource.url",
                postgresContainer::getJdbcUrl
        );
        registry.add(
                "spring.datasource.username",
                postgresContainer::getUsername
        );
        registry.add(
                "spring.datasource.password",
                postgresContainer::getPassword
        );
    }

}
