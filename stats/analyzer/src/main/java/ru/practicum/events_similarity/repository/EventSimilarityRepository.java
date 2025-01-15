package ru.practicum.events_similarity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.events_similarity.model.EventSimilarity;

public interface EventSimilarityRepository extends JpaRepository<EventSimilarity, Long> {

}