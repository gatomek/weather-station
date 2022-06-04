package pl.gatomek.weatherstation;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
@EnableScheduling
public class ScheduledTask {
    final private Repository repository;
    final private RestTemplate restTemplate;
    final private Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
    private Counter okQueryCounter;
    private Counter errQueryCounter;

    @Value("#{environment.OPEN_WEATHER_MAP_APPID}")
    private String openWeatherMapAppId;

    ScheduledTask(Repository repository, RestTemplate restTemplate, MeterRegistry meterRegistry) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        okQueryCounter = meterRegistry.counter( "ok_query_counter");
        errQueryCounter = meterRegistry.counter( "err_query_counter");
    }

    @Scheduled(fixedDelay = 15000)
    @Timed(value = "open-weather-map-query", description = "Open Weather Map Query")
    public void scheduleOpenWeatherMapTask() {
        Double windDegree = -1.;
        Double windSpeed = -1.;
        Double windGust = -1.;

        try {
            String url = "https://api.openweathermap.org/data/2.5/weather?id=3093133&appid=" + openWeatherMapAppId + "&mode=json&lang=pl&units=metrics";
            ResponseEntity<WeatherData> response = restTemplate.getForEntity(url, WeatherData.class);

            okQueryCounter.increment();

            WeatherData weatherData = response.getBody();
            if (Objects.nonNull(weatherData)) {
                Wind wind = weatherData.getWind();
                if (Objects.nonNull(wind)) {
                    windDegree = wind.getDeg();
                    windSpeed = wind.getSpeed();
                    windGust = wind.getGust();
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());

            errQueryCounter.increment();
        } finally {
            repository.update(windDegree, windSpeed, windGust);
        }
    }
}
