package ru.practicum.events_similarity.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.events_similarity.mapper.EventSimilarityMapper;
import ru.practicum.events_similarity.model.EventSimilarity;
import ru.practicum.events_similarity.model.EventSimilarityId;
import ru.practicum.events_similarity.repository.EventSimilarityRepository;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.proto.InteractionsCountRequestProto;
import ru.practicum.ewm.stats.proto.RecommendedEventProto;
import ru.practicum.ewm.stats.proto.SimilarEventsRequestProto;
import ru.practicum.ewm.stats.proto.UserPredictionsRequestProto;
import ru.practicum.user_action.model.ActionType;
import ru.practicum.user_action.model.UserAction;
import ru.practicum.user_action.repository.UserActionRepository;
import ru.practicum.user_action.service.UserActionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventSimilarityServiceImpl implements EventSimilarityService {
    private final EventSimilarityRepository eventSimilarityRepository;
    private final UserActionRepository userActionRepository;
    private final EventSimilarityMapper eventSimilarityMapper;
    private final UserActionService userActionService;

    @Override
    @Transactional
    public void saveEventsSimilarity(List<EventSimilarityAvro> eventsSimilarityAvro) {
        log.info("Save events similarity");

        Map<EventSimilarityId, EventSimilarity> eventsSimilarity = eventSimilarityMapper
                .listEventSimilarityAvroToListEventSimilarity(eventsSimilarityAvro).stream()
                .collect(Collectors.toMap(EventSimilarity::getEventSimilarityId, Function.identity()));

        List<EventSimilarity> eventsSimilarityFromDb = eventSimilarityRepository.findAllById(eventsSimilarity.keySet());

        if (eventsSimilarityFromDb.size() != eventsSimilarityAvro.size()) {
            List<EventSimilarity> newEventSimilarity = eventsSimilarity.values().stream()
                    .filter(eventSimilarity -> !eventsSimilarityFromDb.contains(eventSimilarity))
                    .toList();
            eventSimilarityRepository.saveAll(newEventSimilarity);
        }

        eventsSimilarityFromDb.forEach(eventSimilarity -> eventSimilarity.setScore(eventsSimilarity
                .get(eventSimilarity.getEventSimilarityId()).getScore()));

        log.info("Save events similarity");
    }

    @Override
    public List<RecommendedEventProto> getSimilarEvents(SimilarEventsRequestProto similarEventsRequestProto) {
        log.info("Get similar events");

        List<Long> eventsId = userActionRepository.findEventsIdByUserId(similarEventsRequestProto.getUserId());
        eventsId.remove(similarEventsRequestProto.getEventId());
        List<EventSimilarity> eventSimilarities = eventSimilarityRepository
                .findEventSimilaritiesByEventsId(similarEventsRequestProto.getEventId(), eventsId);

        return eventSimilarityMapper.listEventSimilarityToListRecommendedEventProto(eventSimilarities);
    }

    @Override
    public List<RecommendedEventProto> getInteractionsCount(InteractionsCountRequestProto interactionsCountRequestProto) {
        log.info("Get interactions count");
        List<RecommendedEventProto> recommendedEventProtoList = new ArrayList<>();

        List<Long> eventsId = interactionsCountRequestProto.getEventIdList();
        List<UserAction> usersActionsOnEvents = userActionRepository.findUserActionByEventIdIn(eventsId);

        eventsId.forEach(eventId ->
        {
            double weight = usersActionsOnEvents.stream()
                .filter(userAction -> userAction.getUserActionId().getEventId().equals(eventId))
                .mapToDouble(userAction -> userActionService.getWeight(userAction.getActionType()))
                .reduce(0.0, Double::sum);
            recommendedEventProtoList.add(buildRecommendedEventProto(eventId, weight));
        });

        return recommendedEventProtoList;
    }

    @Override
    public List<RecommendedEventProto> getRecommendationsForUser(UserPredictionsRequestProto userPredictions) {
        log.info("Get recommendations for user");
        List<RecommendedEventProto> recommendedEventProtoList = new ArrayList<>();

        List<Long> eventsId = userActionRepository
                .findEventsIdByUserIdOrderByActionDateDesc(userPredictions.getUserId(), userPredictions.getMaxResults());

        if (eventsId.isEmpty()) {
            return List.of();
        }

        List<EventSimilarity> similarEventsWithoutUserInteraction = eventSimilarityRepository
                .findSimilarEvents(eventsId, userPredictions.getMaxResults());
        List<Long> similarEventsId = similarEventsWithoutUserInteraction.stream()
                .map(eventSimilarity -> eventSimilarity.getEventSimilarityId().getEventB())
                .toList();

        similarEventsId.forEach(eventId -> recommendedEventProtoList
                .add(calculateRatingNewEvent(eventId, eventsId, userPredictions)));

        return recommendedEventProtoList;
    }

    private double calculateEvaluationNewEvent(List<EventSimilarity> similarEventsFromUserInteraction,
                                               Map<Long, ActionType> eventsRating) {
        double sumWeightedEstimates = 0.0;
        double sumSimilarityCoefficients = 0.0;

        for (EventSimilarity eventSimilarity : similarEventsFromUserInteraction) {
            sumWeightedEstimates += eventSimilarity.getScore() * userActionService
                    .getWeight(eventsRating.get(eventSimilarity.getEventSimilarityId().getEventB()));
            sumSimilarityCoefficients += eventSimilarity.getScore();
        }

        return sumWeightedEstimates / sumSimilarityCoefficients;
    }

    private RecommendedEventProto buildRecommendedEventProto(Long eventId, Double score) {
        return RecommendedEventProto.newBuilder()
                .setEventId(eventId)
                .setScore(score)
                .build();
    }

    private RecommendedEventProto calculateRatingNewEvent(Long eventId,
                                                          List<Long> eventsId,
                                                          UserPredictionsRequestProto userPredictions) {
        List<EventSimilarity> similarEventsFromUserInteraction = eventSimilarityRepository
                .findNearestNeighbors(eventId, userPredictions.getMaxResults());
        List<Long> similarEventsIdFromUserInteraction = similarEventsFromUserInteraction.stream()
                .map(eventSimilarity -> eventSimilarity.getEventSimilarityId().getEventB())
                .filter(eventsId::contains)
                .toList();
        Map<Long, ActionType> eventsRating = userActionRepository
                .findUserActionByEventIdIn(similarEventsIdFromUserInteraction).stream()
                .collect(Collectors.toMap(userAction -> userAction.getUserActionId().getEventId(),
                        UserAction::getActionType));
        double evaluationNewEvent = calculateEvaluationNewEvent(similarEventsFromUserInteraction, eventsRating);

        return buildRecommendedEventProto(eventId, evaluationNewEvent);
    }

}
