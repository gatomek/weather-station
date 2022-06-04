package pl.gatomek.weatherstation;

import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.atomic.AtomicLong;

@Component
@Getter
public class Repository {
    private Double deg;
    private Double speed;
    private Double gust;

    private Counter updateCounter;

    private AtomicLong degGauge;
    private AtomicLong speedGauge;
    private AtomicLong gustGauge;

    final private Logger logger = LoggerFactory.getLogger(Repository.class);
    final private KafkaTemplate<String, String> kafkaTemplate;

    public Repository( MeterRegistry meterRegistry, KafkaTemplate<String, String> kafkaTemplate) {
        updateCounter = meterRegistry.counter( "update_counter");

        degGauge = meterRegistry.gauge( "weather_wind_degrees", new AtomicLong( 0));
        speedGauge = meterRegistry.gauge( "weather_wind_speed", new AtomicLong( 0));
        gustGauge = meterRegistry.gauge( "weather_wind_gust", new AtomicLong( 0));

        this.kafkaTemplate = kafkaTemplate;
    }

    private long DoubleToLong(Double d) {
        return Math.round( d);
    }

    public void update(@NotNull Double degree, @NotNull Double speed, @NotNull Double gust) {

        updateCounter.increment();

        synchronized (this) {
            boolean windDegreeChanged = !degree.equals(deg);
            deg = degree;

            boolean changed = windDegreeChanged || !speed.equals(this.speed);
            this.speed = speed;

            changed = changed || !gust.equals(this.gust);
            this.gust = gust;

            updateDataMetrix();

            if (changed) {
                logger.info("Wind changed! degree: " + degree + " | speed: " + speed + " | gust: " + gust);

                if (windDegreeChanged)
                    NotifyWindDirectionChanged( degree);
            }
        }
    }

    private void updateDataMetrix() {
        degGauge.set(DoubleToLong(this.deg));
        speedGauge.set(DoubleToLong(this.speed));
        gustGauge.set(DoubleToLong(this.gust));
    }

    private void NotifyWindDirectionChanged(Double degree)
    {
        Gson gson = new Gson();
        String json = gson.toJson(WindDegreeChangedEvent.of(degree));
        sendMessage("windDegreeChanged", json);
    }

    private void sendMessage(String topic, String message) {

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);

        future.addCallback(
                new ListenableFutureCallback<SendResult<String, String>>() {

                    @Override
                    public void onSuccess(SendResult<String, String> result) {
                        logger.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        logger.error("Unable to send message=[{}] due to: {}", message, ex.getMessage());
                    }
                }
        );
    }
}
