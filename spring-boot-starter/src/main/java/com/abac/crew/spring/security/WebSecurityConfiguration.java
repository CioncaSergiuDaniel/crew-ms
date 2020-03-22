package com.abac.crew.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Class used to configure the Spring Security Basic Authentication
 * the custom service that will create the Principal in the Spring Security Context based on a username and password pair
 *
 * @author sergiu-daniel.cionca
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private CrewBasicAuthentificationEntryPoint crewBasicAuthentificationEntryPoint;

    @Autowired
    private CrewAccessDeniedHandler crewAccessDeniedHandler;


    /**
     * Configure the secured routes, the session policy and the custom service to be used
     * for loading the spring security principal based on username and password
     * <p>
     * All routes under /api/ will be secured and will require the basic authentication on each request due
     * to session policy which is set to STATELESS which means no session will be created hence the username/password
     * must be passed every time
     *
     * @param http - the http security context
     * @throws Exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests().antMatchers("/crew/**", "/swagger-ui.html", "/actuator/**").permitAll().anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedHandler(crewAccessDeniedHandler).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().httpBasic()
                .authenticationEntryPoint(crewBasicAuthentificationEntryPoint);
    }

    /**
     * Configure one test username and password for now
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("crew_emea_test")
                .password(passwordEncoder().encode("test"))
                .authorities("ROLE_USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
