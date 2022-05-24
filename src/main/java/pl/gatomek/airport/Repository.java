package pl.gatomek.airport;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Repository {
    private Logger logger = LoggerFactory.getLogger(Repository.class);
    private String deg;
    private String speed;
    private String gust;

    public boolean update(String deg, String speed, String gust) {
        logger.info(deg + " / " + speed + " / " + gust);

        boolean changed = !deg.equals(this.deg);
        this.deg = deg;

        changed = changed || !speed.equals(this.speed);
        this.speed = speed;

        changed = changed || !gust.equals(this.gust);
        this.gust = gust;

        return changed;
    }
}
