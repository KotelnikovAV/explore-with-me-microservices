package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.proto.*;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class RecommendationsClient {
    @GrpcClient("analyzer")
    private RecommendationsControllerGrpc.RecommendationsControllerBlockingStub recommendationsClient;

    public List<RecommendedEventProto> getSimilarEvents(long eventId, long userId, int maxResults) {
        SimilarEventsRequestProto request = SimilarEventsRequestProto.newBuilder()
                .setEventId(eventId)
                .setUserId(userId)
                .setMaxResults(maxResults)
                .build();

        Iterator<RecommendedEventProto> iterator = recommendationsClient.getSimilarEvents(request);

        return asStream(iterator).toList();
    }

    public List<RecommendedEventProto> getRecommendationsForUser(long userId, int maxResults) {
        UserPredictionsRequestProto request = UserPredictionsRequestProto.newBuilder()
                .setUserId(userId)
                .setMaxResults(maxResults)
                .build();

        Iterator<RecommendedEventProto> iterator = recommendationsClient.getRecommendationsForUser(request);

        return asStream(iterator).toList();
    }

    public List<RecommendedEventProto> getInteractionsCount(List<Long> eventsId) {
        InteractionsCountRequestProto.Builder requestBuilder = InteractionsCountRequestProto.newBuilder();

        for (Long eventId : eventsId) {
            requestBuilder.addEventId(eventId);
        }

        InteractionsCountRequestProto request = requestBuilder.build();
        Iterator<RecommendedEventProto> iterator = recommendationsClient.getInteractionsCount(request);

        return asStream(iterator).toList();
    }

    private Stream<RecommendedEventProto> asStream(Iterator<RecommendedEventProto> iterator) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED),
                false
        );
    }
}
