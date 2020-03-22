package com.abac.crew.bm.repository;

import com.abac.crew.bm.entity.Crew;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Mono;

public interface CrewRepository extends ReactiveCassandraRepository<Crew, Long> {

    @AllowFiltering
    Mono<Boolean> existsByExternalIdIs(final String crewExternalId);
}
