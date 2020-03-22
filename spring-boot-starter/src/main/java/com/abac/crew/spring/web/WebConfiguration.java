package com.abac.crew.spring.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * Configuration class to allow Cross-Origin Resource Sharing
 *
 * @author sergiu-daniel.cionca
 */
@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * Adds CORS support
     *
     * @param registry - the web registry for adding the URLs
     *                 for which to enable CORS
     */
    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE");
    }

    /**
     * Configures the message converter for all http responses
     *
     * @param converters - the list of converters to which is added the jackson message converter
     */
    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
        final StringHttpMessageConverter converter = new StringHttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN));
        converters.add(new ResourceHttpMessageConverter());

        // for text/plain types
        converters.add(converter);

        final HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(
                new Jackson2ObjectMapperBuilder()
                        .serializationInclusion(JsonInclude.Include.NON_NULL)
                        .build());

        // for application/json types
        converters.add(jsonConverter);
    }
}
