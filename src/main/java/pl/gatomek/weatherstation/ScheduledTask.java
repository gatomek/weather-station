package pl.gatomek.weatherstation;

import io.micrometer.core.annotation.Timed;
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
    @Value("#{environment.OPEN_WEATHER_MAP_APPID}")
    private String openWeatherMapAppId;
    ScheduledTask(Repository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedDelay = 15000)
    @Timed(value = "weather-query", description = "Weather Query")
    public void scheduleOpenWeatherMapTask() {
        try {
            String url = "https://api.openweathermap.org/data/2.5/weather?id=3093133&appid=" + openWeatherMapAppId + "&mode=json&lang=pl&units=metrics";
            ResponseEntity<WeatherData> response = restTemplate.getForEntity(url, WeatherData.class);

            WeatherData weatherData = response.getBody();
            if (Objects.nonNull(weatherData)) {
                Wind wind = weatherData.getWind();
                if (Objects.nonNull(wind)) {
                    String deg = wind.getDeg();
                    String speed = wind.getSpeed();
                    String gust = wind.getGust();

                    boolean changed = repository.update(deg, speed, gust);
                    if (changed)
                        logger.info("deg: " + deg + " | speed: " + speed + " | gust: " + gust);
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}
