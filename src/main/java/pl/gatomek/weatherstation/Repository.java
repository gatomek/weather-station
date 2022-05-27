package pl.gatomek.weatherstation;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Getter
public class Repository {
    private String deg;
    private String speed;
    private String gust;
    private Counter update_counter;
    private AtomicLong deg_gauge;
    private AtomicLong speed_gauge;
    private AtomicLong gust_gauge;

    public Repository( MeterRegistry meterRegistry) {
        update_counter = meterRegistry.counter( "update_counter");

        deg_gauge = meterRegistry.gauge( "weather_wind_degrees", new AtomicLong( 0));
        speed_gauge = meterRegistry.gauge( "weather_wind_speed", new AtomicLong( 0));
        gust_gauge = meterRegistry.gauge( "weather_wind_gust", new AtomicLong( 0));
    }

    private long CastStringToLong( String s) {
        double d = Double.parseDouble( s);
        return Math.round( d);
    }

    public boolean update(String deg, String speed, String gust) {
        synchronized (this) {
            update_counter.increment();

            boolean changed = !deg.equals(this.deg);
            this.deg = deg;
            deg_gauge.set( CastStringToLong( deg));

            changed = changed || !speed.equals(this.speed);
            this.speed = speed;
            speed_gauge.set( CastStringToLong( speed));

            changed = changed || !gust.equals(this.gust);
            this.gust = gust;
            gust_gauge.set( CastStringToLong( gust));

            return changed;
        }
    }
}
