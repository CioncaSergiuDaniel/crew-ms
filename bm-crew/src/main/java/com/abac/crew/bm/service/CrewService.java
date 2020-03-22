package com.abac.crew.bm.service;

import com.abac.crew.bm.metrics.BmMetricsService;
import com.abac.crew.bm.repository.CrewRepository;
import com.abac.crew.planet.consumer.service.PlanetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CrewService {

    private final PlanetService planetService;

    private final BmMetricsService bmMetricsService;

    private final CrewRepository crewRepository;

    public Mono<Boolean> existsByExternalId(final String externalId) {
        return crewRepository.existsByExternalIdIs(externalId);
    }

    public void assignCrewToPlanet(final String planetExternalId, final String crewExternalId) {
        planetService.assignCrewToPlanet(crewExternalId, planetExternalId);

        // increment metrics
        bmMetricsService.incrementAssignedCrews();
    }
}
