package com.abac.crew.controller;

import com.abac.crew.bm.constraints.UniqueCrewConstraintById;
import com.abac.crew.bm.service.CrewService;
import com.abac.crew.common.constants.HeaderConstants;
import com.abac.crew.common.exception.ErrorTO;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * Crew v1 APIs
 *
 * @author sergiu-daniel.cionca
 */
@Api(value = "Crew API v1", tags = "Crew API (v1)", authorizations = @Authorization(value = "basic"))
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class CrewManagementV1Controller {

    private final CrewService crewService;

    @ApiOperation(value = "Assign crew to planet.", notes = "Planet can have only one crew assigned at a time.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Crew assigned successfully to planet.", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Bad request.", response = ErrorTO.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorTO.class), })
    @PostMapping(value = "/api/v1/crew/assign", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> assignCrewToPlanet(
            @RequestHeader(name = HeaderConstants.CREW_ID) @UniqueCrewConstraintById(message = "Crew with provided id is not available!!!") final String crewId,
            @RequestHeader(name = HeaderConstants.PLANET_ID) @NotNull(message = "Planet id must not be null!") final String planetId) {
        log.info("Attempt to assign crew with id: {} to planet with id: {}", crewId, planetId);

        crewService.assignCrewToPlanet(planetId, crewId);

        log.info("Successfully assigned crew with id: {} to planet with id: {}", crewId, planetId);

        return ResponseEntity.ok().build();
    }
}
