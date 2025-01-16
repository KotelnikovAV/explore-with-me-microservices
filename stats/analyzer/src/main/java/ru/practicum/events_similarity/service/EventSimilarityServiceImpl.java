package ru.practicum.events_similarity.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.events_similarity.mapper.EventSimilarityMapper;
import ru.practicum.events_similarity.model.EventSimilarity;
import ru.practicum.events_similarity.repository.EventSimilarityRepository;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.proto.InteractionsCountRequestProto;
import ru.practicum.ewm.stats.proto.RecommendedEventProto;
import ru.practicum.ewm.stats.proto.SimilarEventsRequestProto;
import ru.practicum.ewm.stats.proto.UserPredictionsRequestProto;
import ru.practicum.user_action.model.ActionType;
import ru.practicum.user_action.model.UserAction;
import ru.practicum.user_action.repository.UserActionRepository;

import java.util.ArrayList;
import java.util.List;

import static ru.practicum.utility.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventSimilarityServiceImpl implements EventSimilarityService {
    private final EventSimilarityRepository eventSimilarityRepository;
    private final UserActionRepository userActionRepository;
    private final EventSimilarityMapper eventSimilarityMapper;

    @Override
    public void saveEventsSimilarity(List<EventSimilarityAvro> eventsSimilarityAvro) {
        log.info("Save events similarity");

        List<EventSimilarity> eventsSimilarity = eventSimilarityMapper
                .listEventSimilarityAvroToListEventSimilarity(eventsSimilarityAvro);
        eventSimilarityRepository.saveAll(eventsSimilarity);
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
        List<UserAction> usersAction = userActionRepository.findUserActionByEventIdIn(eventsId);

        for (Long eventId : eventsId) {
            double weight = usersAction.stream()
                    .filter(userAction -> userAction.getEventId().equals(eventId))
                    .map(userAction -> getWeight(userAction.getActionType()))
                    .reduce(0.0, Double::sum);
            RecommendedEventProto recommendedEventProto = RecommendedEventProto.newBuilder()
                    .setEventId(eventId)
                    .setScore(weight)
                    .build();
            recommendedEventProtoList.add(recommendedEventProto);
        }

        return recommendedEventProtoList;
    }

    @Override
    public List<RecommendedEventProto> getRecommendationsForUser(UserPredictionsRequestProto userPredictions) {
        log.info("Get recommendations for user");

        List<Long> eventsId = userActionRepository
                .findEventsIdByUserIdOrderByActionDateDesc(userPredictions.getUserId(), userPredictions.getMaxResults());

        if (eventsId.isEmpty()) {
            return List.of();
        }

        List<EventSimilarity> eventsSimilarity = eventSimilarityRepository
                .findEventSimilaritiesByEventsIdNotIn(eventsId, userPredictions.getMaxResults());

        return List.of();
    }

    private double getWeight(ActionType actionType) {
        switch (actionType) {
            case VIEW -> {
                return VIEWING_RATIO;
            }
            case REGISTER -> {
                return REGISTRATION_RATIO;
            }
            case LIKE -> {
                return LIKE_RATIO;
            }
            default -> {
                return 0.0;
            }
        }
    }

}
