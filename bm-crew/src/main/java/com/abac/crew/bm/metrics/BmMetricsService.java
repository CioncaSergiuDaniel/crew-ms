package com.abac.crew.bm.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * metrics provided by bm-crew
 * exposed trough actuator
 *
 * @author sergiu-daniel.cionca
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BmMetricsService {

    private final MeterRegistry meterRegistry;

    public void incrementAssignedCrews() {
        meterRegistry.counter("assigned_planet_crew").increment();
    }
}
