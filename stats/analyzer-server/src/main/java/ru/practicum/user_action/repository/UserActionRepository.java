package ru.practicum.user_action.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.user_action.model.UserAction;

import java.util.List;

public interface UserActionRepository extends JpaRepository<UserAction, Long> {

    @Query("select ua.eventId " +
            "from UserAction as ua " +
            "where ua = :userId " +
            "group by ua.actionDate " +
            "order by ua.actionDate desc " +
            "limit :limit ")
    List<Long> findEventsIdByUserIdOrderByActionDateDesc(@Param("userId") Long userId, @Param("limit") int limit);

    List<Long> findEventsIdByUserId(Long userId);

    List<UserAction> findUserActionByEventIdIn(List<Long> eventsId);
}