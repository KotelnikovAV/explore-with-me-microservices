package ru.practicum.events_similarity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.events_similarity.model.EventSimilarity;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventSimilarityMapper {
    EventSimilarity eventSimilarityAvroToEventSimilarity(EventSimilarityAvro eventSimilarityAvro);

    List<EventSimilarity> listEventSimilarityAvroToListEventSimilarity(List<EventSimilarityAvro> eventsSimilarityAvro);
}
