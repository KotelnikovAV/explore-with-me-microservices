package ru.practicum.events_similarity.starter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.events_similarity.consumer.EventSimilarityConsumerService;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnalyzerEventSimilarityStarter {
    private final EventSimilarityConsumerService eventSimilarityConsumerService;

    public void start() {
        log.info("Starting analyzer event similarity consumer service");
        eventSimilarityConsumerService.consumeEventsSimilarity();
    }
}
