package ru.practicum.actions.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.util.List;

public interface AggregatorService {
    List<EventSimilarityAvro> handleRecord(ConsumerRecord<Void, UserActionAvro> record);
}
