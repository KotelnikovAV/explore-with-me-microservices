package ru.practicum.events_similarity.controller;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.practicum.events_similarity.service.EventSimilarityService;
import ru.practicum.ewm.stats.proto.*;

import java.util.List;

@GrpcService
@Slf4j
@RequiredArgsConstructor
public class RecommendationsController extends RecommendationsControllerGrpc.RecommendationsControllerImplBase {
    private final EventSimilarityService eventSimilarityService;

    @Override
    public void getRecommendationsForUser(UserPredictionsRequestProto userPredictionsRequestProto,
                                          StreamObserver<RecommendedEventProto> responseObserver) {
        log.info("get recommendations for user");

        List<RecommendedEventProto> recommendsEventProto = eventSimilarityService
                .getRecommendationsForUser(userPredictionsRequestProto);

        for (RecommendedEventProto recommendedEventProto : recommendsEventProto) {
            responseObserver.onNext(recommendedEventProto);
        }

        responseObserver.onCompleted();
    }

    @Override
    public void getSimilarEvents(SimilarEventsRequestProto similarEventsRequestProto,
                                 StreamObserver<RecommendedEventProto> responseObserver) {
        log.info("get similar events");

        List<RecommendedEventProto> recommendsEventProto = eventSimilarityService
                .getSimilarEvents(similarEventsRequestProto);

        for (RecommendedEventProto recommendedEventProto : recommendsEventProto) {
            responseObserver.onNext(recommendedEventProto);
        }

        responseObserver.onCompleted();
    }

    @Override
    public void getInteractionsCount(InteractionsCountRequestProto similarEventsRequestProto,
                                     StreamObserver<RecommendedEventProto> responseObserver) {
        log.info("get interactions count");

        List<RecommendedEventProto> recommendsEventProto = eventSimilarityService
                .getInteractionsCount(similarEventsRequestProto);

        for (RecommendedEventProto recommendedEventProto : recommendsEventProto) {
            responseObserver.onNext(recommendedEventProto);
        }

        responseObserver.onCompleted();
    }
}
