package ru.practicum.events_similarity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.events_similarity.model.EventSimilarity;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.proto.RecommendedEventProto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class EventSimilarityMapper {
    public abstract EventSimilarity eventSimilarityAvroToEventSimilarity(EventSimilarityAvro eventSimilarityAvro);

    public abstract List<EventSimilarity> listEventSimilarityAvroToListEventSimilarity(List<EventSimilarityAvro> eventsSimilarityAvro);

    public RecommendedEventProto eventSimilarityToRecommendedEventProto(EventSimilarity eventSimilarity) {
        return RecommendedEventProto.newBuilder()
                .setEventId(eventSimilarity.getEventB())
                .setScore(eventSimilarity.getScore())
                .build();
    }

    public abstract List<RecommendedEventProto> listEventSimilarityToListRecommendedEventProto(List<EventSimilarity> eventSimilarities);
}
