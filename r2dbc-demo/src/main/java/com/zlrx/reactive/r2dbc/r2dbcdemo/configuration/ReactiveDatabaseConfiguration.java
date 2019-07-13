package com.zlrx.reactive.r2dbc.r2dbcdemo.configuration;

import com.zlrx.reactive.r2dbc.r2dbcdemo.repository.PersonRepository;
import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories(basePackageClasses = PersonRepository.class)
public class ReactiveDatabaseConfiguration extends AbstractR2dbcConfiguration {

    @Override
    public ConnectionFactory connectionFactory() {
        return new H2ConnectionFactory(
                H2ConnectionConfiguration.builder()
                        .username("sa")
                        .inMemory("reactive")
                        .build()
        );
    }
}
