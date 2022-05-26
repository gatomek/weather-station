package pl.gatomek.weatherstation.configuration;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class EmbeddedTomcatConfiguration implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        String port = System.getenv("PORT");
        if (Objects.nonNull(port))
            factory.setPort(Integer.parseInt(port));
    }
}
