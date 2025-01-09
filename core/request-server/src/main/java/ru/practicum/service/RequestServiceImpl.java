package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.event.EventClient;
import ru.practicum.client.user.UserClient;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.requests.ParticipationRequestDto;
import ru.practicum.enums.State;
import ru.practicum.enums.Status;
import ru.practicum.exception.IntegrityViolationException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.Request;
import ru.practicum.repository.RequestsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {
    private final RequestsRepository requestsRepository;
    private final RequestMapper requestMapper;
    private final UserClient userClient;
    private final EventClient eventClient;

    @Override
    public List<ParticipationRequestDto> findAllRequestsByUserId(long userId) {
        log.info("The beginning of the process of finding all requests");

        if (!userClient.getUserExists(userId)) {
            throw new NotFoundException("User with id=" + userId + " was not found");
        }

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

        if (!userClient.getUserExists(userId)) {
            throw new NotFoundException("User with id=" + userId + " was not found");
        }

        if (eventClient.findExistEventByEventIdAndInitiatorId(eventId, userId)) {
            throw new IntegrityViolationException("UserId " + userId + " initiates  eventId " + eventId);
        }

        EventFullDto event = eventClient.findEventById(eventId);

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
        request.setRequesterId(userId);
        request.setEventId(eventId);

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

        if (!userClient.getUserExists(userId)) {
            throw new NotFoundException("User with id=" + userId + " was not found");
        }

        Request request = requestsRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request with id = " + requestId + " not found"));

        request.setStatus(Status.CANCELED);
        log.info("The request has been canceled");
        return requestMapper.requestToParticipationRequestDto(request);
    }

    @Override
    public List<ParticipationRequestDto> findAllRequestsByEventId(Long eventId) {
        log.info("The beginning of the process of finding all requests by events");

        List<Request> requests = requestsRepository.findByEventId(eventId);

        log.info("The all requests has been found");
        return requestMapper.listRequestToListParticipationRequestDto(requests);
    }

    @Override
    public List<ParticipationRequestDto> findAllRequestsByRequestsId(Set<Long> requestsId) {
        log.info("The beginning of the process of finding all requests by requestsId");

        List<Request> requests = requestsRepository.findByIdIn(requestsId);

        log.info("The all requests has been found");
        return requestMapper.listRequestToListParticipationRequestDto(requests);
    }

    @Override
    @Transactional
    public List<ParticipationRequestDto> updateRequest(Set<Long> requestsId, String status) {
        log.info("The beginning of the process of updating a request");

        List<Request> requests = requestsRepository.findByIdIn(requestsId);
        requests.forEach(request -> request.setStatus(Status.valueOf(status)));

        log.info("The request has been updated");
        return requestMapper.listRequestToListParticipationRequestDto(requests);
    }

    @Override
    public boolean findExistRequests(Long eventId, Long userId, String status) {
        log.info("The beginning of the process of finding exist requests");
        return requestsRepository.existsByEventIdAndRequesterIdAndStatus(eventId, userId, Status.valueOf(status));
    }

    @Override
    public List<ParticipationRequestDto> findAllRequestsByEventIdAndStatus(Long eventId, String status) {
        log.info("The beginning of the process of finding all requests by eventId");

        List<Request> requests = requestsRepository.findAllByStatusAndEventId(Status.valueOf(status), eventId);

        log.info("The all requests has been found");
        return requestMapper.listRequestToListParticipationRequestDto(requests);
    }
}
