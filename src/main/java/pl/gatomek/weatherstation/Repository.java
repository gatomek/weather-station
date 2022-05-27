package pl.gatomek.weatherstation;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Getter
public class Repository {
    private String deg;
    private String speed;
    private String gust;
    private Counter update_counter;
    private AtomicInteger deg_gauge;
    private AtomicInteger speed_gauge;
    private AtomicInteger gust_gauge;

    public Repository( MeterRegistry meterRegistry) {
        update_counter = meterRegistry.counter( "update_counter");

        deg_gauge = meterRegistry.gauge( "weather_wind_degrees", new AtomicInteger( 0));
        speed_gauge = meterRegistry.gauge( "weather_wind_speed", new AtomicInteger( 0));
        gust_gauge = meterRegistry.gauge( "weather_wind_gust", new AtomicInteger( 0));
    }

    public boolean update(String deg, String speed, String gust) {
        synchronized (this) {
            update_counter.increment();

            boolean changed = !deg.equals(this.deg);
            this.deg = deg;
            deg_gauge.set( Integer.parseInt(deg));

            changed = changed || !speed.equals(this.speed);
            this.speed = speed;
            speed_gauge.set( Integer.parseInt(speed));

            changed = changed || !gust.equals(this.gust);
            this.gust = gust;
            gust_gauge.set( Integer.parseInt(gust));

            return changed;
        }
    }
}
