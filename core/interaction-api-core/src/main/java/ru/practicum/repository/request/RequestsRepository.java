package ru.practicum.repository.request;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.enums.Status;
import ru.practicum.model.event.Event;
import ru.practicum.model.request.Request;
import ru.practicum.model.user.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RequestsRepository extends JpaRepository<Request, Long> {

    List<Request> findByEventId(long eventId);

    List<Request> findByIdIn(Set<Long> id);

    List<Request> findAllByRequesterId(long id);

    Optional<Request> findByEventIdAndRequesterId(long eventId, long requesterId);

    List<Request> findAllByStatusAndEventId(Status status, long eventId);

    boolean existsByEventAndRequesterAndStatus(Event event, User requester, Status status);
}