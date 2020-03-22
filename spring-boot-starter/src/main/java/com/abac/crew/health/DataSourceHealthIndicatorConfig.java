package com.abac.crew.health;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.cassandra.CassandraReactiveHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;

/**
 * DataSource Health Indicator
 *
 * @author sergiu-daniel.cionca
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DataSourceHealthIndicatorConfig {

    private final ReactiveCassandraOperations cassandraOperations;

    @Bean
    public CassandraReactiveHealthIndicator cassandraHealthStatus() {
        return new CassandraReactiveHealthIndicator(cassandraOperations);
    }
}
