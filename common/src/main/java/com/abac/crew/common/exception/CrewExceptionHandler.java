package com.abac.crew.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Generic exception handler for common status codes
 *
 * @author sergiu-daniel.cionca
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class CrewExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String CONSTRAINT_VIOLATION_STATUS_CODE = "400";

    /**
     * Handles the generic API exceptions
     *
     * @param ex
     *         - the {@link ApplicationException}
     *
     * @return - a {@link ResponseEntity} containing a friendly format showing the cause of the exception
     */
    @ExceptionHandler(value = { ApplicationException.class })
    public ResponseEntity<ErrorTO> handleGenericAPIException(final ApplicationException ex) {

        return ResponseEntity.status(ex.getStatusCode()).body(new ErrorTO(String.valueOf(ex.getErrorCode()), ex.getMessage()));
    }

    /**
     * Handles constrain violation exceptions
     *
     * @param ex
     *         - the {@link ConstraintViolationException}
     *
     * @return - a {@link ResponseEntity} containing a friendly format showing the cause of the exception
     */
    @ExceptionHandler(value = { ConstraintViolationException.class })
    public ResponseEntity<ErrorTO> handleConstraintViolationException(final ConstraintViolationException ex) {
        final Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        final ErrorTO response;
        if (constraintViolations != null) {
            final String errorMessage = constraintViolations.stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            response = new ErrorTO(CONSTRAINT_VIOLATION_STATUS_CODE, errorMessage);
        } else {
            response = new ErrorTO(CONSTRAINT_VIOLATION_STATUS_CODE, ex.getMessage());
        }

        return ResponseEntity.status(Integer.valueOf(CONSTRAINT_VIOLATION_STATUS_CODE)).body(response);
    }
}
