package ru.practicum.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Properties;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("aggregator.kafka.producer")
public class ProducerConfig {
    private Map<String, String> properties;
    private Map<String, String> topics;
    private long timeUntilClosingKafkaProducerMs;

    public Producer<String, SpecificRecordBase> getKafkaProducer() {
       return new KafkaProducer<>(getPropertiesForKafkaProducer());
    }

    private Properties getPropertiesForKafkaProducer() {
        Properties config = new Properties();
        for (String key : properties.keySet()) {
            config.put(key, properties.get(key));
        }
        return config;
    }
}