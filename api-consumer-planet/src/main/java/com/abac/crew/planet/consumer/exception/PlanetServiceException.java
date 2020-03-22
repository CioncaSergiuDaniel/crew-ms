package com.abac.crew.planet.consumer.exception;

import com.abac.crew.common.exception.ApplicationException;

/**
 * generic planet Service Exception to wrap all specific exceptions
 *
 * @author sergiu-daniel.cionca
 */
public class PlanetServiceException extends ApplicationException {


    /**
     * Planet Service Exception constructor with status code and message
     *
     * @param statusCode
     * @param message
     */
    public PlanetServiceException(final int statusCode, final int errorCode, final String message, final boolean resilienceAffectingException) {
        super(statusCode, errorCode, message, resilienceAffectingException);
    }

    public static class EmptyResponse extends PlanetServiceException {
        public EmptyResponse() {
            super(404, 404, "Planet Response is null", false);
        }
    }

    public static class ProviderRequestTimeout extends PlanetServiceException {
        public ProviderRequestTimeout() {
            super(500, 901, "Request to external system timed out!", false);
        }
    }

    public static class InternalServiceError extends PlanetServiceException {
        public InternalServiceError() {
            super(500, 900, "Internal service error", false);
        }
    }

    public static class ProviderRequestError extends PlanetServiceException {
        public ProviderRequestError() {
            super(500, 902, "Request to external system failed", false);
        }
    }
}
