package ru.practicum.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.StatClient;
import ru.practicum.config.AppConfig;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.event.NewEventDto;
import ru.practicum.dto.event.UpdateEventUserRequest;
import ru.practicum.dto.requests.EventRequestStatusUpdateRequestDto;
import ru.practicum.dto.requests.EventRequestStatusUpdateResultDto;
import ru.practicum.dto.requests.ParticipationRequestDto;
import ru.practicum.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventPrivateController {
    private final EventService eventService;
    private final StatClient statClient;
    private final AppConfig appConfig;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addEvent(@RequestBody @Valid NewEventDto newEventDto, @PathVariable long userId) {
        log.info("Received a POST request to add event {} from a user with an userId = {}", newEventDto, userId);
        return eventService.addEvent(newEventDto, userId);
    }

    @GetMapping("/{eventId}")
    public EventFullDto findEventById(@PathVariable long userId,
                                      @PathVariable long eventId,
                                      HttpServletRequest request) {
        log.info("Received a GET request to find event by id {} from a user with an userId = {}", eventId, userId);
        EventFullDto event = eventService.findEventById(userId, eventId);
        EndpointHitDto endpointHitDto = new EndpointHitDto(
                appConfig.getAppName(),
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now());
        statClient.save(endpointHitDto);
        return event;
    }


    @GetMapping
    public List<EventShortDto> findEventsByUser(@PathVariable long userId,
                                                @RequestParam(defaultValue = "0") int from,
                                                @RequestParam(defaultValue = "10") int size,
                                                HttpServletRequest request) {
        log.info("Received a GET request to find events by userId = {} from = {} size = {}", userId, from, size);
        List<EventShortDto> events = eventService.findEventsByUser(userId, from, size);
        EndpointHitDto endpointHitDto = new EndpointHitDto(
                appConfig.getAppName(),
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now());
        statClient.save(endpointHitDto);
        return events;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@RequestBody @Valid UpdateEventUserRequest updateEventUserRequest,
                                    @PathVariable long userId,
                                    @PathVariable long eventId) {
        log.info("Received a PATCH request to update event with an eventId = {} from a user with an userId = {}, " +
                "request body {}", eventId, userId, updateEventUserRequest);
        return eventService.updateEvent(updateEventUserRequest, userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> findRequestByEventId(@PathVariable long userId, @PathVariable long eventId) {
        log.info("Received a GET request to find request by event id = {} from a user with an userId = {}",
                eventId, userId);
        return eventService.findRequestByEventId(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResultDto updateRequestByEventId(@RequestBody
                                                                    @Valid
                                                                    EventRequestStatusUpdateRequestDto updateRequests,
                                                                    @PathVariable long userId,
                                                                    @PathVariable long eventId) {
        log.info("Received a PATCH request to update request with an eventId = {} from a user with an userId = {}, " +
                "request body {}", eventId, userId, updateRequests);
        return eventService.updateRequestByEventId(updateRequests, userId, eventId);
    }
}
