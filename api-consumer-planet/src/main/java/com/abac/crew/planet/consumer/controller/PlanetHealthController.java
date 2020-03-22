package com.abac.crew.planet.consumer.controller;

import com.abac.crew.common.exception.ApplicationException;
import com.abac.crew.common.exception.feign.FeignClientBase;
import com.abac.crew.planet.consumer.controller.configuration.PlanetServiceFeignConfig;
import feign.RequestLine;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * external crew service REST controller
 *
 * @author sergiu-daniel.cionca
 */
@FeignClient(name = "crewHealthController", url = "${integration.planet.service.endpoint}",
        configuration = PlanetServiceFeignConfig.class)
public interface PlanetHealthController extends FeignClientBase {

    /**
     * get the health status from planet service
     */
    @RequestLine(value = "GET /actuator/health")
    Response checkHealth() throws ApplicationException;
}
