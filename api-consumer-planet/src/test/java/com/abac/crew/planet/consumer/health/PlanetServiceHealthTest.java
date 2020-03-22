package com.abac.crew.planet.consumer.health;

import com.abac.crew.common.exception.ApplicationException;
import com.abac.crew.planet.consumer.controller.PlanetHealthController;
import com.abac.crew.planet.consumer.controller.configuration.PlanetServiceConfig;
import feign.Request;
import feign.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PlanetServiceHealthTest {

    @InjectMocks
    private PlanetServiceHealth planetServiceHealth;

    @Mock
    private PlanetHealthController planetHealthController;

    @Mock
    private PlanetServiceConfig planetServiceConfig;

    @Test
    public void health_messagingServiceIsHealthy_returnUPResponse() {
        // given
        final Response response = Response.builder().status(200).request(Request.create(Request.HttpMethod.GET, "", new HashMap<>(), null)).build();
        when(planetHealthController.checkHealth()).thenReturn(response);
        when(planetServiceConfig.getUrl()).thenReturn("http://localhost:8080/planet");

        // when
        final Health health = planetServiceHealth.health();

        // then
        assertNotNull(health);
        assertEquals(Status.UP, health.getStatus());
    }

    @Test
    public void health_messagingServiceIsUnHealthy_returnDOWNResponse() {
        // given
        final Response response = Response.builder().status(500).request(Request.create(Request.HttpMethod.GET, "", new HashMap<>(), null)).build();
        when(planetHealthController.checkHealth()).thenReturn(response);
        when(planetServiceConfig.getUrl()).thenReturn("http://localhost:8080/planet");

        // when
        final Health health = planetServiceHealth.health();

        // then
        assertNotNull(health);
        assertEquals(Status.DOWN, health.getStatus());
    }

    @Test
    public void health_messagingServiceThrowsException_returnDOWNResponse() {
        // given
        Mockito.doThrow(new ApplicationException.InternalServerError()).when(planetHealthController).checkHealth();
        when(planetServiceConfig.getUrl()).thenReturn("http://localhost:8080/planet");

        // when
        final Health health = planetServiceHealth.health();

        // then
        assertNotNull(health);
        assertEquals(Status.DOWN, health.getStatus());
    }
}
