package ru.practicum.events_similarity.service;

import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.proto.InteractionsCountRequestProto;
import ru.practicum.ewm.stats.proto.RecommendedEventProto;
import ru.practicum.ewm.stats.proto.SimilarEventsRequestProto;
import ru.practicum.ewm.stats.proto.UserPredictionsRequestProto;

import java.util.List;

public interface EventSimilarityService {
    void saveEventsSimilarity(List<EventSimilarityAvro> eventsSimilarityAvro);

    List<RecommendedEventProto> getSimilarEvents(SimilarEventsRequestProto similarEventsRequestProto);

    List<RecommendedEventProto> getInteractionsCount(InteractionsCountRequestProto interactionsCountRequestProto);

    List<RecommendedEventProto> getRecommendationsForUser(UserPredictionsRequestProto userPredictionsRequestProto);
}
