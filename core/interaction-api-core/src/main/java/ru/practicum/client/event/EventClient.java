package ru.practicum.client.event;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.*;
import ru.practicum.dto.requests.EventRequestStatusUpdateRequestDto;
import ru.practicum.dto.requests.EventRequestStatusUpdateResultDto;
import ru.practicum.dto.requests.ParticipationRequestDto;
import ru.practicum.enums.EventPublicSort;
import ru.practicum.enums.State;
import ru.practicum.fallback.event.EventFallback;
import ru.practicum.utility.Constants;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "event", fallback = EventFallback.class)
public interface EventClient {
    @GetMapping("/api/v1/admin/events")
    List<EventFullDto> getAllAdminEvents(@RequestParam(required = false) List<Long> users,
                                         @RequestParam(required = false) State state,
                                         @RequestParam(required = false) List<Long> categories,
                                         @RequestParam(required = false)
                                         @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT)
                                         LocalDateTime rangeStart,
                                         @RequestParam(required = false)
                                         @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT)
                                         LocalDateTime rangeEnd,
                                         @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                         @RequestParam(defaultValue = "10") @Positive int size,
                                         @RequestParam(defaultValue = "true") Boolean sortRating);

    @PatchMapping("/api/v1/admin/events/{eventId}")
    EventFullDto updateEventAdmin(@RequestBody @Valid UpdateEventAdminRequest updateEventAdminRequest,
                                  @PathVariable long eventId);

    @PostMapping("/api/v1/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    EventFullDto addEvent(@RequestBody @Valid NewEventDto newEventDto, @PathVariable long userId);

    @GetMapping("/api/v1/users/{userId}/events/{eventId}")
    EventFullDto findEventById(@PathVariable long userId,
                               @PathVariable long eventId,
                               HttpServletRequest request);

    @GetMapping("/api/v1/users/{userId}/events")
    List<EventShortDto> findEventsByUser(@PathVariable long userId,
                                         @RequestParam(defaultValue = "0") int from,
                                         @RequestParam(defaultValue = "10") int size,
                                         HttpServletRequest request);

    @PatchMapping("/api/v1/users/{userId}/events/{eventId}")
    EventFullDto updateEvent(@RequestBody @Valid UpdateEventUserRequest updateEventUserRequest,
                             @PathVariable long userId,
                             @PathVariable long eventId);

    @GetMapping("/api/v1/users/{userId}/events/{eventId}/requests")
    List<ParticipationRequestDto> findRequestByEventId(@PathVariable long userId, @PathVariable long eventId);

    @PatchMapping("/api/v1/users/{userId}/events/{eventId}/requests")
    EventRequestStatusUpdateResultDto updateRequestByEventId(@RequestBody
                                                             @Valid
                                                             EventRequestStatusUpdateRequestDto updateRequests,
                                                             @PathVariable long userId,
                                                             @PathVariable long eventId);

    @GetMapping("/api/v1/events")
    List<EventShortDto> getAllPublicEvents(@RequestParam(required = false) String text,
                                           @RequestParam(required = false) List<Long> categories,
                                           @RequestParam(required = false) Boolean paid,
                                           @RequestParam(required = false)
                                           @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT)
                                           LocalDateTime rangeStart,
                                           @RequestParam(required = false)
                                           @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT)
                                           LocalDateTime rangeEnd,
                                           @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                           @RequestParam(defaultValue = "EVENT_DATE") EventPublicSort sort,
                                           @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                           @RequestParam(defaultValue = "10") @Positive Integer size,
                                           HttpServletRequest request);

    @GetMapping("/api/v1/events/{id}")
    EventFullDto getPublicEventById(@PathVariable Long id, HttpServletRequest request);

    @GetMapping("/api/v1/events/category/{catId}")
    boolean getExistEventByCategory(@PathVariable Long catId);
}
