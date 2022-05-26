package pl.gatomek.weatherstation;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class WeatherData {
    private Wind wind;
}

@Setter
@Getter
class Wind {
    private String deg;
    private String gust;
    private String speed;
    String getGust() {
        if(Objects.nonNull( gust))
            return gust;
        return "0";
    }
}
