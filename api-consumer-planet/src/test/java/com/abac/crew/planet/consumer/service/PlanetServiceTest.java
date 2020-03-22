package com.abac.crew.planet.consumer.service;

import com.abac.crew.planet.consumer.controller.configuration.PlanetServiceConfig;
import com.abac.crew.planet.consumer.controller.configuration.PlanetServiceFeignConfig;
import com.abac.crew.planet.consumer.exception.PlanetServiceException;
import com.abac.crew.planet.consumer.exception.PlanetServiceExceptionMapper;
import com.github.tomakehurst.wiremock.http.Fault;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"integration.planet.service.endpoint=http://localhost:${wiremock.server.port}",
                "integration.planet.service.certificate.check=false",
                "planet.feign.connectTimeout=10",
                "planet.feign.readTimeout=2000"},
        classes = {PlanetService.class, PlanetServiceFeignConfig.class, PlanetServiceConfig.class, PlanetServiceExceptionMapper.class})
@AutoConfigureWireMock(port = 0)
@EnableAutoConfiguration
public class PlanetServiceTest {

    @Autowired
    private PlanetService planetService;

    @Test
    public void testAssignCrewToPlanet() {
        setGlobalFixedDelay(0);

        stubFor(post(urlPathMatching("/api/v1/planet/assign"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=UTF-8")));

        planetService.assignCrewToPlanet("crewId", "planetId");

        verify(postRequestedFor(urlEqualTo("/api/v1/planet/assign"))
                .withHeader("Authorization", matching(".*")));
    }

    @Test
    public void test_CDPMessagingSendEmailNotificationToSpecificReceiver_Failure() {
        setGlobalFixedDelay(0);

        stubFor(post(urlPathMatching("/api/v1/planet/assign")).willReturn(aResponse().withFault(Fault.EMPTY_RESPONSE)));

        try {
            planetService.assignCrewToPlanet("crewId", "planetId");
            Assert.fail("ApplicationException expected");
        } catch (final PlanetServiceException.InternalServiceError e) {
            verify(postRequestedFor(
                    urlEqualTo("/api/v1/planet/assign"))
                    .withHeader("Authorization", matching(".*")));
        }
    }

    @Test
    public void test_CDPMessagingSendNotificationToCustomer_Timeout() {
        stubFor(post(urlPathMatching("/api/v1/planet/assign"))
                .willReturn(aResponse().withBody("{}").withStatus(200)
                        .withFixedDelay(3000).withHeader("Content-Type", "application/json; charset=UTF-8")));

        try {
            planetService.assignCrewToPlanet("crewId", "planetId");
            Assert.fail("ApplicationException expected");
        } catch (final PlanetServiceException.ProviderRequestTimeout e) {
            verify(postRequestedFor(urlEqualTo("/api/v1/planet/assign"))
                    .withHeader("Authorization", matching(".*")));
        }
    }

    @Test
    public void test_CDPMessagingSendNotificationToCustomer_Failure() {
        stubFor(post(urlPathMatching("/api/v1/planet/assign")).willReturn(aResponse().withFault(Fault.EMPTY_RESPONSE)));

        try {
            planetService.assignCrewToPlanet("crewId", "planetId");

            Assert.fail("ApplicationException expected");
        } catch (final PlanetServiceException.InternalServiceError e) {
            verify(postRequestedFor(urlEqualTo("/api/v1/planet/assign"))
                    .withHeader("Authorization", matching(".*")));
        }
    }

}
