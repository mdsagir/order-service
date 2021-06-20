package com.example.config;

import io.r2dbc.pool.PoolingConnectionFactoryProvider;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.time.Duration;

@Configuration
@EnableR2dbcAuditing
@EnableR2dbcRepositories
public class MySqlConfig {

    @Bean
    public ConnectionFactory fromOption() {

        ConnectionFactoryOptions connectionFactoryOptions = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.HOST, "35.192.18.170")
                .option(ConnectionFactoryOptions.DATABASE, "springnative")
                .option(ConnectionFactoryOptions.USER, "sagir")
                .option(ConnectionFactoryOptions.PASSWORD, "password")
                .option(ConnectionFactoryOptions.DRIVER, "mysql")
                .option(PoolingConnectionFactoryProvider.MAX_CREATE_CONNECTION_TIME, Duration.ofSeconds(5))
                .option(PoolingConnectionFactoryProvider.INITIAL_SIZE, 5)
                .option(PoolingConnectionFactoryProvider.MAX_SIZE, 10)
                .option(PoolingConnectionFactoryProvider.POOL_NAME, PoolingConnectionFactoryProvider.POOLING_DRIVER)
                .build();
        return ConnectionFactories.get(connectionFactoryOptions);
    }

}