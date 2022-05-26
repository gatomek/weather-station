package pl.gatomek.weatherstation;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Repository {
    private String deg;
    private String speed;
    private String gust;

    public boolean update(String deg, String speed, String gust) {
        synchronized (this) {
            boolean changed = !deg.equals(this.deg);
            this.deg = deg;

            changed = changed || !speed.equals(this.speed);
            this.speed = speed;

            changed = changed || !gust.equals(this.gust);
            this.gust = gust;

            return changed;
        }
    }
}
