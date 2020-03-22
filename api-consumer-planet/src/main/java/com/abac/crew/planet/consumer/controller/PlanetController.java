package com.abac.crew.planet.consumer.controller;

import com.abac.crew.common.exception.ApplicationException;
import com.abac.crew.common.exception.feign.FeignClientBase;
import com.abac.crew.planet.consumer.controller.configuration.PlanetServiceFeignConfig;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.ResponseEntity;

/**
 * external planet service REST controller
 *
 * @author sergiu-daniel.cionca
 */
@CircuitBreaker(name = "planetServiceCommand")
@FeignClient(name = "planetController", url = "${integration.planet.service.endpoint}", configuration = PlanetServiceFeignConfig.class)
@EnableAspectJAutoProxy
public interface PlanetController extends FeignClientBase {

    @RequestLine(value = "POST /api/v1/planet/assign")
    @Headers({"CREW_ID: {crewId}", "PLANET_ID: {planetId}"})
    ResponseEntity assignCrewToPlanet(@Param("crewId") final String crewExternalId, @Param("planetId") final String planetExternalId) throws ApplicationException;

}
