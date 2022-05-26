package pl.gatomek.airport;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    RestTemplate makeRestTemplate()
    {
        return buildRestTemplate(Duration.ofSeconds( 5));
    }

    private RestTemplate buildRestTemplate(Duration duration) {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        return builder.setConnectTimeout(duration).setReadTimeout(duration).build();
    }

}
