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
class Wind {
    private Double deg;
    private Double gust;
    private Double speed;

    public Double getDeg() {
        if(Objects.nonNull( deg))
            return deg;

        return -1.;
    }

    public Double getGust() {
        if(Objects.nonNull( gust))
            return gust;

        return 0.;
    }

    public Double getSpeed() {
        if(Objects.nonNull( speed))
            return speed;

        return 0.;
    }
}
