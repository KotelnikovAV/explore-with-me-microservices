package ru.practicum.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.VoidDeserializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.time.Duration;
import java.util.Map;
import java.util.Properties;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("aggregator.kafka.consumer")
public class ConsumerConfig {
    private Map<String, String> properties;
    private Map<String, String> topics;
    private long consumeAttemptTimeout;

    public Consumer<Void, UserActionAvro> getConsumer() {
       return new KafkaConsumer<>(getPropertiesForKafkaConsumer());
    }

    public Duration getConsumeTimeout() {
        return Duration.ofMillis(consumeAttemptTimeout);
    }

    private Properties getPropertiesForKafkaConsumer() {
        Properties config = new Properties();
        for (String key : properties.keySet()) {
            config.put(key, properties.get(key));
        }
        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, VoidDeserializer.class.getCanonicalName());
        return config;
    }
}