package pl.gatomek.weatherstation;

import com.sun.istack.internal.NotNull;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
@Getter
public class Repository {
    private Double deg;
    private Double speed;
    private Double gust;
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

    private long DoubleToLong(Double d) {
        return Math.round( d);
    }

    public boolean update(@NotNull Double degree, @NotNull Double speed, @NotNull Double gust) {
        synchronized (this) {
            update_counter.increment();

            boolean changed = ! degree.equals(this.deg);
            this.deg = degree;
            deg_gauge.set( DoubleToLong( degree));

            changed = changed || ! speed.equals(this.speed);
            this.speed = speed;
            speed_gauge.set( DoubleToLong( speed));

            changed = changed || ! gust.equals(this.gust);
            this.gust = gust;
            gust_gauge.set( DoubleToLong( gust));

            return changed;
        }
    }
}
