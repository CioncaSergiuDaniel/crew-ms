package com.abac.crew.spring.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthentificationEntryPoint for the httpbasic authentification mechanism.
 *
 * @author sergiu-daniel.cionca
 */
@Component
@Slf4j
public class CrewBasicAuthentificationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void afterPropertiesSet() {
        setRealmName("crew");
        super.afterPropertiesSet();
    }

    /**
     * Handles the unauthorized event
     *
     * @param request
     *         - the request
     * @param response
     *         - the response
     * @param authException
     *         - the authentication exception
     *
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response,
            final AuthenticationException authException) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(response.getOutputStream(), HttpStatus.FORBIDDEN.getReasonPhrase());

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.getOutputStream().flush();
    }
}
