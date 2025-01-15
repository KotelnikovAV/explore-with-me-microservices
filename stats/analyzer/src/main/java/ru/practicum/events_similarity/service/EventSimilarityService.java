package ru.practicum.events_similarity.service;

import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

import java.util.List;

public interface EventSimilarityService {
    void saveEventsSimilarity(List<EventSimilarityAvro> eventsSimilarityAvro);
}
