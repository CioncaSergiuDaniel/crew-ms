package com.abac.crew.planet.consumer.exception;

import com.abac.crew.common.exception.ThrowableUtils;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.net.SocketTimeoutException;

@Aspect
@Slf4j
public class PlanetServiceExceptionMapper {

    private final ThrowableUtils throwableUtils = new ThrowableUtils();

    @Around("execution(* com.abac.crew.planet.consumer.controller.PlanetController..*(..))")
    public Object mapException(final ProceedingJoinPoint pjp) throws Throwable {
        try {
            final Object result = pjp.proceed();
            if (result == null) {
                throw new PlanetServiceException.EmptyResponse();
            }
            return result;
        } catch (final FeignException ex) {
            log.error(ex.getMessage(), ex);
            if (throwableUtils.hasCause(ex, SocketTimeoutException.class)) {
                throw new PlanetServiceException.ProviderRequestTimeout();
            } else {
                throw new PlanetServiceException.InternalServiceError();
            }
        } catch (final CallNotPermittedException ex) {
            log.error(ex.getMessage(), ex);
            throw new PlanetServiceException.ProviderRequestError();
        }
    }
}
