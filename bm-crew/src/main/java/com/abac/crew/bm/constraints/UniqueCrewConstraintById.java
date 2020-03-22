package com.abac.crew.bm.constraints;


import com.abac.crew.bm.constraints.validators.UniqueCrewValidatorById;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {UniqueCrewValidatorById.class,})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, })
public @interface UniqueCrewConstraintById {

    /**
     * Used to set the error message
     *
     * @return message when the instance fails the validation
     */
    String message() default "Planet already exists ${id}";

    Class<?>[] groups() default {};

    /**
     * Optional payloads for the constraint
     *
     * @return the assigned payloads if any
     */
    Class<? extends Payload>[] payload() default {};
}
