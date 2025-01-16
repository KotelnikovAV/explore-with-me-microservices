package ru.practicum.events_similarity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.events_similarity.model.EventSimilarity;

import java.util.List;

public interface EventSimilarityRepository extends JpaRepository<EventSimilarity, Long> {

    @Query("select es " +
            "from EventSimilarity as es " +
            "where (es.eventA = :targetEventId or es.eventB = :targetEventId) and (es.eventB not in :eventsId and es.eventA not in :eventsId) " +
            "group by es.score " +
            "order by es.score ")
    List<EventSimilarity> findEventSimilaritiesByEventsId(@Param("targetEventId") Long targetEventId,
                                                          @Param("eventsId") List<Long> eventsId);

    @Query("select es " +
            "from EventSimilarity as es " +
            "where es.eventB not in :eventsId and es.eventA not in :eventsId " +
            "group by es.score " +
            "order by es.score " +
            "limit :limit ")
    List<EventSimilarity> findEventSimilaritiesByEventsIdNotIn(@Param("eventsId") List<Long> eventsId,
                                                               @Param("limit") int limit);

    @Query("select es " +
            "from EventSimilarity as es " +
            "where es.eventB in :eventsId or es.eventA in :eventsId " +
            "group by es.score " +
            "order by es.score " +
            "limit :limit ")
    List<EventSimilarity> findEventSimilaritiesByEventsIdIn(@Param("eventsId") List<Long> eventsId,
                                                            @Param("limit") int limit);
}