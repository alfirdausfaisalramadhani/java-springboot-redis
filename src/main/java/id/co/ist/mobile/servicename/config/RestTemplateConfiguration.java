package id.co.ist.mobile.servicename.config;

import ch.qos.logback.classic.Level;
import id.co.ist.mobile.common.config.logger.ControllerLogger;
import id.co.ist.mobile.common.config.logger.RestTemplateLogger;
import id.co.ist.mobile.common.exception.MobileRestExceptionHandler;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Slf4j
@Import(ControllerLogger.class)
@Configuration
public class RestTemplateConfiguration {

    private final CloseableHttpClient httpClient;
    @Value(value = "${rest.client.connect.timeout:3}")
    private int restClientConnectTimeout;
    @Value(value = "${rest.client.read.timeout:5}")
    private int restClientReadTimeout;
    @Value(value = "${logging.level.id.co.ist.mobile.resttemplate.logger:DEBUG")
    private String restClientLoggerLevel;

    @Autowired
    public RestTemplateConfiguration(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {

        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectionRequestTimeout((restClientConnectTimeout * 1000));
        clientHttpRequestFactory.setConnectTimeout((restClientConnectTimeout * 1000));
        clientHttpRequestFactory.setReadTimeout((restClientReadTimeout * 1000));
        clientHttpRequestFactory.setBufferRequestBody(true);
        clientHttpRequestFactory.setHttpClient(httpClient);
        return new BufferingClientHttpRequestFactory(clientHttpRequestFactory);
    }

    @Bean
    public RestTemplate restTemplate() {
        List<ClientHttpRequestInterceptor> interceptors;

        if (Level.DEBUG.levelStr.equals(restClientLoggerLevel)) {
            interceptors = Collections.singletonList(new RestTemplateLogger());
        } else {
            interceptors = Collections.emptyList();
        }

        return new RestTemplateBuilder()
                .requestFactory(this::clientHttpRequestFactory)
                .setConnectTimeout(Duration.ofSeconds(restClientConnectTimeout))
                .setReadTimeout(Duration.ofSeconds(restClientReadTimeout))
                .errorHandler(new MobileRestExceptionHandler<>(ApiResponse.class))
                .interceptors(interceptors)
                .build();
    }
}

