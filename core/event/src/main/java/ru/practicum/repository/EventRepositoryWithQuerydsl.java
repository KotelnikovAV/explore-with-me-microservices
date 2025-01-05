package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.enums.State;
import ru.practicum.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepositoryWithQuerydsl extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {
    Optional<Event> findByIdAndState(Long id, State state);

    List<Event> findAllByIdIn(List<Long> ids);

    List<Event> findAllByCategoryId(Long categoryId);

    Optional<Event> findByIdAndInitiatorId(Long id, Long initiatorId);

}
