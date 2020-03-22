package com.abac.crew.bm.constraints.validators;

import com.abac.crew.bm.constraints.UniqueCrewConstraintById;
import com.abac.crew.bm.service.CrewService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UniqueCrewValidatorById implements ConstraintValidator<UniqueCrewConstraintById, String> {

    private final CrewService crewService;

    @Override
    public void initialize(final UniqueCrewConstraintById constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String externalId, final ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = false;
        if (StringUtils.isNotEmpty(externalId) && crewService.existsByExternalId(externalId).block()) {
            valid = true;
        }

        return valid;
    }
}