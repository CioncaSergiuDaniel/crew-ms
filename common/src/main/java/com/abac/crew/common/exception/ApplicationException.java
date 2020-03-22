package com.abac.crew.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

/**
 * generic API Exception wrapper
 *
 * @author sergiu-daniel.cionca
 */
public class ApplicationException extends RuntimeException {

    @Getter
    private int errorCode;

    @Getter
    private int statusCode;

    @Getter
    private boolean resilienceAffecting;

    /**
     * Generic API Exception constructor with status code and message
     *
     * @param statusCode
     * @param message
     */
    public ApplicationException(final int statusCode, final int errorCode, final String message, final boolean resilienceAffecting) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.resilienceAffecting = resilienceAffecting;
    }

    /**
     * Generic API Exception constructor by HttpStatus and resilience affection
     *
     * @param httpStatus
     * @param resilienceAffecting
     */
    public ApplicationException(final HttpStatus httpStatus, final boolean resilienceAffecting) {
        this(httpStatus.value(), httpStatus.value(), httpStatus.getReasonPhrase(), resilienceAffecting);
    }

    /**
     * Generic API Exception constructor by HttpStatus, defaulting to not affecting resilience
     *
     * @param httpStatus
     */
    public ApplicationException(final HttpStatus httpStatus) {
        this(httpStatus, false);
    }

    public static class BadRequest extends ApplicationException {
        public BadRequest() {
            super(BAD_REQUEST);
        }
    }

    public static class Unauthorized extends ApplicationException {
        public Unauthorized() {
            super(UNAUTHORIZED);
        }
    }

    public static class Forbidden extends ApplicationException {
        public Forbidden() {
            super(FORBIDDEN);
        }
    }

    public static class NotFound extends ApplicationException {
        public NotFound() {
            super(NOT_FOUND);
        }
    }

    public static class InternalServerError extends ApplicationException {
        public InternalServerError() {
            super(INTERNAL_SERVER_ERROR, true);
        }
    }

    public static class BadGateway extends ApplicationException {
        public BadGateway() {
            super(BAD_GATEWAY, true);
        }
    }

    public static class ServiceUnavailable extends ApplicationException {
        public ServiceUnavailable() {
            super(SERVICE_UNAVAILABLE, true);
        }
    }
}
