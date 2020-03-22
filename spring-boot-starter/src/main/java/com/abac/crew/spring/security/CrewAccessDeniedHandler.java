package com.abac.crew.spring.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom handler for forbidden access exceptions thrown by spring security
 *
 * @author sergiu-daniel.cionca
 */
@Component
public class CrewAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Handles the access denied exception
     *
     * @param httpServletRequest
     *         - the request
     * @param httpServletResponse
     *         - the response
     * @param e
     *         - the exception
     *
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse,
            final AccessDeniedException e) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.writeValue(httpServletResponse.getOutputStream(), HttpStatus.FORBIDDEN.getReasonPhrase());

        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getOutputStream().flush();
    }
}
