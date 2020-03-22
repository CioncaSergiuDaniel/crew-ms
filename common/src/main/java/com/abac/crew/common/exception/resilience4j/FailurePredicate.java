package com.abac.crew.common.exception.resilience4j;

import com.abac.crew.common.exception.ApplicationException;

import java.util.function.Predicate;

/**
 * Generic Resilience4j fail predicate which will test exceptions thrown in the application
 *
 * @author sergiu-daniel.cionca
 */
public class FailurePredicate implements Predicate<Throwable> {

    /**
     * check if circuit breaker should open or not
     *
     * @param throwable
     *         exception thrown in the application that must be checked
     *
     * @return true in case the exception should affect circuit breaker state
     */
    @Override
    public boolean test(final Throwable throwable) {
        boolean circuitBreakerAffected = false;

        if (throwable instanceof ApplicationException) {
            final ApplicationException exception = (ApplicationException) throwable;
            circuitBreakerAffected = exception.isResilienceAffecting();
        } else if (throwable instanceof Exception) {
            circuitBreakerAffected = true;
        }

        return circuitBreakerAffected;
    }
}
