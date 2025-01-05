package ru.practicum.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.enums.State;
import ru.practicum.model.event.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByIdAndState(Long id, State state);

    List<Event> findAllByIdIn(List<Long> ids);

    List<Event> findAllByCategoryId(Long categoryId);

    Optional<Event> findByIdAndInitiatorId(Long id, Long initiatorId);
}
