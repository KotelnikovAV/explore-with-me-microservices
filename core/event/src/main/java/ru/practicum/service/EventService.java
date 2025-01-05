package ru.practicum.service;

import ru.practicum.dto.event.*;
import ru.practicum.dto.requests.EventRequestStatusUpdateRequestDto;
import ru.practicum.dto.requests.EventRequestStatusUpdateResultDto;
import ru.practicum.dto.requests.ParticipationRequestDto;
import ru.practicum.enums.EventPublicSort;
import ru.practicum.enums.State;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventFullDto addEvent(NewEventDto newEventDto, long userId);

    EventFullDto findEventById(long userId, long eventId);

    List<EventShortDto> findEventsByUser(long userId, int from, int size);

    EventFullDto updateEvent(UpdateEventUserRequest updateEventUserRequest, long userId, long eventId);

    List<ParticipationRequestDto> findRequestByEventId(long userId, long eventId);

    EventRequestStatusUpdateResultDto updateRequestByEventId(EventRequestStatusUpdateRequestDto updateRequest,
                                                             long userId,
                                                             long eventId);

    List<EventShortDto> getAllPublicEvents(String text, List<Long> categories, Boolean paid,
                                           LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                           boolean onlyAvailable, EventPublicSort sort, int from, int size);

    EventFullDto getPublicEventById(long id);

    List<EventFullDto> getAllAdminEvents(List<Long> users, State state, List<Long> categories, LocalDateTime rangeStart,
                                         LocalDateTime rangeEnd, int from, int size, boolean sortRating);

    EventFullDto updateEventAdmin(UpdateEventAdminRequest updateEventAdminRequest, long eventId);
}
