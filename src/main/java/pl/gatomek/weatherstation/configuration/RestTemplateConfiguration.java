package pl.gatomek.weatherstation.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    RestTemplate makeRestTemplate(@Value("${connection.timeout}") int timeout) {
        return buildRestTemplate(Duration.ofSeconds(timeout));
    }

    private RestTemplate buildRestTemplate(Duration timeout) {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        return builder.setConnectTimeout(timeout).setReadTimeout(timeout).build();
    }

}
