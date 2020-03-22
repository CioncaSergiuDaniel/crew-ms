package com.abac.crew.common.exception.feign;

import com.abac.crew.common.exception.ApplicationException;
import feign.error.ErrorCodes;
import feign.error.ErrorHandling;

/**
 * Base feign client interface with common exceptions mapped to error codes
 *
 * @author sergiu-daniel.cionca
 */
@ErrorHandling(codeSpecific = {
        @ErrorCodes(codes = { 400 }, generate = ApplicationException.BadRequest.class),
        @ErrorCodes(codes = { 401 }, generate = ApplicationException.Unauthorized.class),
        @ErrorCodes(codes = { 403 }, generate = ApplicationException.Forbidden.class),
        @ErrorCodes(codes = { 404 }, generate = ApplicationException.NotFound.class),
        @ErrorCodes(codes = { 502 }, generate = ApplicationException.BadGateway.class),
        @ErrorCodes(codes = { 503 }, generate = ApplicationException.ServiceUnavailable.class), },
        defaultException = ApplicationException.InternalServerError.class)
public interface FeignClientBase {
}
