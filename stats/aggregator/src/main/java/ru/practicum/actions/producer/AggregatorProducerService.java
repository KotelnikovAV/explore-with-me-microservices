package ru.practicum.actions.producer;

import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

public interface AggregatorProducerService {
    void aggregateUserActions(EventSimilarityAvro message);
}
