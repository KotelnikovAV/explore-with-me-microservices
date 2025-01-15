package ru.practicum.events_similarity.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.events_similarity.mapper.EventSimilarityMapper;
import ru.practicum.events_similarity.model.EventSimilarity;
import ru.practicum.events_similarity.repository.EventSimilarityRepository;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventSimilarityServiceImpl implements EventSimilarityService {
    private final EventSimilarityRepository eventSimilarityRepository;
    private final EventSimilarityMapper eventSimilarityMapper;

    @Override
    public void saveEventsSimilarity(List<EventSimilarityAvro> eventsSimilarityAvro) {
        log.info("Save events similarity");

        List<EventSimilarity> eventsSimilarity = eventSimilarityMapper
                .listEventSimilarityAvroToListEventSimilarity(eventsSimilarityAvro);
        eventSimilarityRepository.saveAll(eventsSimilarity);
        log.info("Save events similarity");
    }
}
