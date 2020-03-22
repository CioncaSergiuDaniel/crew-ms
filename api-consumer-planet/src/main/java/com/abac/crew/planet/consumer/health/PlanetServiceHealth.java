package com.abac.crew.planet.consumer.health;

import com.abac.crew.planet.consumer.controller.PlanetHealthController;
import com.abac.crew.planet.consumer.controller.configuration.PlanetServiceConfig;
import feign.Response;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Planet Service Health check
 *
 * @author sergiu-daniel.cionca
 */
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PlanetServiceHealth implements HealthIndicator {

    private final PlanetHealthController planetHealthController;

    private final PlanetServiceConfig planetServiceConfig;

    private static final String URL = "URL";

    /**
     * call planet service on /health endpoint end extract the status from outcome property
     *
     * @return {@link Health}
     */
    @Override
    public Health health() {
        Health health;
        try {
            final Response response = planetHealthController.checkHealth();

            health = Health.status(response.status() == 200 ? "UP" : "DOWN").withDetail(URL, planetServiceConfig.getUrl()).build();
        } catch (final Exception ex) {
            health = Health.down(ex).withDetail(URL, planetServiceConfig.getUrl()).build();
        }

        return health;
    }
}
