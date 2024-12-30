package ru.practicum.requests.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.IntegrityViolationException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.dto.mapper.RequestMapper;
import ru.practicum.requests.model.Request;
import ru.practicum.requests.model.Status;
import ru.practicum.requests.repository.RequestsRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {
    private final RequestsRepository requestsRepository;
    private final RequestMapper requestMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getAllRequests(long userId) {
        log.info("The beginning of the process of finding all requests");
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                "User with id = " + userId + " not found"));
        List<Request> requests = requestsRepository.findAllByRequesterId(userId);
        log.info("The all requests has been found");
        return requestMapper.listRequestToListParticipationRequestDto(requests);
    }

    @Override
    @Transactional
    public ParticipationRequestDto addRequest(long userId, long eventId) {
        log.info("The beginning of the process of creating a request");
        requestsRepository.findByEventIdAndRequesterId(eventId, userId).ifPresent(
                r -> {
                    throw new IntegrityViolationException(
                            "Request with userId " + userId + " eventId " + eventId + " exists");
                });

        eventRepository.findByIdAndInitiatorId(eventId, userId).ifPresent(
                r -> {
                    throw new IntegrityViolationException(
                            "UserId " + userId + " initiates  eventId " + eventId);
                });

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                "User with id = " + userId + " not found"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(
                "Event with id = " + eventId + " not found"));

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new IntegrityViolationException("Event with id = " + eventId + " is not published");
        }

        List<Request> confirmedRequests = requestsRepository.findAllByStatusAndEventId(Status.CONFIRMED, eventId);

        if ((!event.getParticipantLimit().equals(0L))
                && (event.getParticipantLimit() == confirmedRequests.size())) {
            throw new IntegrityViolationException("Request limit exceeded");
        }

        Request request = new Request();
        request.setCreated(LocalDateTime.now());
        request.setRequester(user);
        request.setEvent(event);

        if ((event.getParticipantLimit().equals(0L)) || (!event.getRequestModeration())) {
            request.setStatus(Status.CONFIRMED);
        } else {
            request.setStatus(Status.PENDING);
        }

        request = requestsRepository.save(request);
        log.info("The request has been created");
        return requestMapper.requestToParticipationRequestDto(request);
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        log.info("The beginning of the process of canceling a request");
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                "User with id = " + userId + " not found"));
        Request request = requestsRepository.findById(requestId).orElseThrow(() -> new NotFoundException(
                "Request with id = " + requestId + " not found"
        ));
        request.setStatus(Status.CANCELED);
        log.info("The request has been canceled");
        return requestMapper.requestToParticipationRequestDto(request);
    }
}
