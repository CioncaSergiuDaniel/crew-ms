package com.abac.crew.planet.consumer.service;

import com.abac.crew.common.exception.ApplicationException;
import com.abac.crew.planet.consumer.controller.PlanetController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PlanetService {

    private final PlanetController planetController;

    public void assignCrewToPlanet(final String crewExternalId, final String planetExternalId) throws ApplicationException {
        planetController.assignCrewToPlanet(crewExternalId, planetExternalId);
    }
}
