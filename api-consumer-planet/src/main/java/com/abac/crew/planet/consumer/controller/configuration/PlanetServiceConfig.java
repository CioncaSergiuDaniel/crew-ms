package com.abac.crew.planet.consumer.controller.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Planet service configs without which application should not start
 *
 * @author sergiu-daniel.cionca
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Validated
public class PlanetServiceConfig {

    @Value("${integration.planet.service.endpoint}")
    @Getter
    private String url;

    @Value("${integration.planet.service.username}")
    @Getter
    @NotNull
    private String username;

    @Value("${integration.planet.service.password}")
    @Getter
    @NotNull
    private String password;
}
