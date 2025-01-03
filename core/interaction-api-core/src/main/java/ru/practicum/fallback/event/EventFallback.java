package ru.practicum.fallback.event;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.client.event.EventClient;
import ru.practicum.dto.event.*;
import ru.practicum.dto.requests.EventRequestStatusUpdateRequestDto;
import ru.practicum.dto.requests.EventRequestStatusUpdateResultDto;
import ru.practicum.dto.requests.ParticipationRequestDto;
import ru.practicum.enums.EventPublicSort;
import ru.practicum.enums.State;
import ru.practicum.exception.ServerUnavailableException;

import java.time.LocalDateTime;
import java.util.List;

public class EventFallback implements EventClient {
    @Override
    public List<EventFullDto> getAllAdminEvents(List<Long> users,
                                                State state,
                                                List<Long> categories,
                                                LocalDateTime rangeStart,
                                                LocalDateTime rangeEnd,
                                                int from,
                                                int size,
                                                Boolean sortRating) {
        throw new ServerUnavailableException("Endpoint /api/v1/admin/events method GET is unavailable");
    }

    @Override
    public EventFullDto updateEventAdmin(UpdateEventAdminRequest updateEventAdminRequest, long eventId) {
        throw new ServerUnavailableException("Endpoint /api/v1/admin/events/{eventId} method PATCH is unavailable");
    }

    @Override
    public EventFullDto addEvent(NewEventDto newEventDto, long userId) {
        throw new ServerUnavailableException("Endpoint /api/v1/users/{userId}/events method POST is unavailable");
    }

    @Override
    public EventFullDto findEventById(long userId, long eventId, HttpServletRequest request) {
        throw new ServerUnavailableException("Endpoint /api/v1/users/{userId}/events/{eventId} method GET " +
                "is unavailable");
    }

    @Override
    public List<EventShortDto> findEventsByUser(long userId, int from, int size, HttpServletRequest request) {
        throw new ServerUnavailableException("Endpoint /api/v1/users/{userId}/events method GET is unavailable");
    }

    @Override
    public EventFullDto updateEvent(UpdateEventUserRequest updateEventUserRequest, long userId, long eventId) {
        throw new ServerUnavailableException("Endpoint /api/v1/users/{userId}/events/{eventId} method PATCH " +
                "is unavailable");
    }

    @Override
    public List<ParticipationRequestDto> findRequestByEventId(long userId, long eventId) {
        throw new ServerUnavailableException("Endpoint /api/v1/users/{userId}/events/{eventId}/requests method GET " +
                "is unavailable");
    }

    @Override
    public EventRequestStatusUpdateResultDto updateRequestByEventId(EventRequestStatusUpdateRequestDto updateRequests,
                                                                    long userId,
                                                                    long eventId) {
        throw new ServerUnavailableException("Endpoint /api/v1/users/{userId}/events/{eventId}/requests method PATCH " +
                "is unavailable");
    }

    @Override
    public List<EventShortDto> getAllPublicEvents(String text,
                                                  List<Long> categories,
                                                  Boolean paid,
                                                  LocalDateTime rangeStart,
                                                  LocalDateTime rangeEnd,
                                                  Boolean onlyAvailable,
                                                  EventPublicSort sort,
                                                  Integer from,
                                                  Integer size,
                                                  HttpServletRequest request) {
        throw new ServerUnavailableException("Endpoint /api/v1/events method GET is unavailable");
    }

    @Override
    public EventFullDto getPublicEventById(Long id, HttpServletRequest request) {
        throw new ServerUnavailableException("Endpoint /api/v1/events/{id} method GET is unavailable");
    }

    @Override
    public boolean getExistEventByCategory(Long catId) {
        throw new ServerUnavailableException("Endpoint /api/v1/events/category/{catId} method GET is unavailable");
    }
}
