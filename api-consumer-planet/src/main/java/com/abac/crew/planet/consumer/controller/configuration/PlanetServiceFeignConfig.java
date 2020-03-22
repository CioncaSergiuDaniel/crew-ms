package com.abac.crew.planet.consumer.controller.configuration;

import com.abac.crew.planet.consumer.controller.PlanetController;
import feign.Client;
import feign.Contract;
import feign.Logger;
import feign.Request;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.error.AnnotationErrorDecoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.util.concurrent.TimeUnit;

/**
 * Planet configuration needed for rest calls done to external planet service
 *
 * @author sergiu-daniel.cionca
 */
@Configuration
@EnableFeignClients(basePackages = "com.abac.crew")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PlanetServiceFeignConfig {

    private final PlanetServiceConfig planetServiceConfig;

    /**
     * create a feign decoder to scan the annotations and throw appropriate Exception to the status code
     *
     * @return {@link ErrorDecoder}
     */
    @Bean
    public ErrorDecoder feignErrorDecoder() {
        final AnnotationErrorDecoder.Builder builder = AnnotationErrorDecoder.builderFor(PlanetController.class);
        builder.withResponseBodyDecoder(decoder());

        return builder.build();
    }

    /**
     * build the feign client without certificate check/validation
     *
     * @return {@link Client}
     */
    @Bean
    @ConditionalOnProperty(value = "skip.planet.service.certificate.check", havingValue = "true")
    public Client feignClient() {
        return new Client.Default(getSSLSocketFactory(), new NoopHostnameVerifier());
    }

    /**
     * basic auth provider
     *
     * @return {@link BasicAuthRequestInterceptor}
     */
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(planetServiceConfig.getUsername(),
                planetServiceConfig.getPassword());
    }

    /**
     * get default feign contract
     *
     * @return {@link Contract}
     */
    @Bean
    public Contract feignContract() {
        return new feign.Contract.Default();
    }

    /**
     * connection/read timeouts used for REST calls to crew service
     *
     * @return {@link Request.Options}
     */
    @Bean
    public Request.Options options(@Value("${planet.feign.connectTimeout:10000}") final int connectTimeout,
            @Value("${planet.feign.readTimeout:60000}") final int readTimeout) {
        return new Request.Options(connectTimeout, TimeUnit.MILLISECONDS, readTimeout, TimeUnit.MILLISECONDS, true);
    }

    /**
     * provide a generic encoder
     *
     * @return {@link GsonEncoder}
     */
    @Bean
    public Encoder encoder() {
        return new GsonEncoder();
    }

    /**
     * provide a generic decoder
     *
     * @return {@link GsonDecoder}
     */
    @Bean
    public Decoder decoder() {
        return new GsonDecoder();
    }

    /**
     * create a log level FULL to print request, headers and response from Planet Service only in case the log level is DEBUG
     *
     * @return {@link Logger.Level}
     */
    @Bean
    @ConditionalOnProperty(value = "com.abac.crew.planet.consumer.controller.CrewController", havingValue = "DEBUG")
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * disable certificate check/validation
     *
     * @return {@link SSLSocketFactory}
     */
    private SSLSocketFactory getSSLSocketFactory() {
        try {
            final TrustStrategy acceptingTrustStrategy = (chain, authType) -> true;

            final SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            return sslContext.getSocketFactory();
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
