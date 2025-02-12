package ru.eventlink.event.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.eventlink.client.event.EventClient;
import ru.eventlink.dto.event.EventFullDto;
import ru.eventlink.dto.event.UpdateEventAdminRequest;
import ru.eventlink.enums.State;
import ru.eventlink.event.service.EventService;
import ru.eventlink.utility.Constants;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/events")
@Slf4j
@RequiredArgsConstructor
public class EventAdminController implements EventClient {
    private final EventService eventService;

    @GetMapping
    @Override
    public List<EventFullDto> findAllAdminEvents(@RequestParam(required = false) List<Long> users,
                                                 @RequestParam(required = false) State states,
                                                 @RequestParam(required = false) List<Long> categories,
                                                 @RequestParam(required = false)
                                                 @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT)
                                                 LocalDateTime rangeStart,
                                                 @RequestParam(required = false)
                                                 @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT)
                                                 LocalDateTime rangeEnd,
                                                 @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                 @RequestParam(defaultValue = "10") @Positive int size,
                                                 @RequestParam(defaultValue = "true") Boolean sortRating) {
        log.info("Get all admin events by users {}, state {}, categories {}, rangeStart {}, rangeEnd {}, from {}, " +
                "size {}, sortRating {}", users, states, categories, rangeStart, rangeEnd, from, size, sortRating);
        return eventService.findAllAdminEvents(users, states, categories, rangeStart, rangeEnd, from, size, sortRating);
    }

    @PatchMapping("/{eventId}")
    @Override
    public EventFullDto updateEventAdmin(@RequestBody @Valid UpdateEventAdminRequest updateEventAdminRequest,
                                         @PathVariable long eventId) {
        log.info("Received a PATCH request to update event with an eventId = {} request body {}",
                eventId, updateEventAdminRequest);
        return eventService.updateEventAdmin(updateEventAdminRequest, eventId);
    }

    @GetMapping("/{eventId}")
    @Override
    public EventFullDto findEventById(@PathVariable Long eventId) {
        log.info("Get event by id {}", eventId);
        return eventService.findEventById(eventId);
    }

    @GetMapping("/{eventId}/existence/{initiatorId}")
    @Override
    public boolean findExistEventByEventIdAndInitiatorId(@PathVariable @Positive Long eventId,
                                                         @PathVariable @Positive Long initiatorId) {
        log.info("Get existence event by eventId {} and initiatorId {}", eventId, initiatorId);
        return eventService.findExistEventByEventIdAndInitiatorId(eventId, initiatorId);
    }

    @GetMapping("/{eventId}/existence/")
    @Override
    public boolean findExistEventByEventId(@PathVariable @Positive Long eventId) {
        log.info("Get existence event by eventId {}", eventId);
        return eventService.findExistEventByEventId(eventId);
    }

    @PutMapping("/{eventId}")
    @Override
    public void updateRatingEvent(@PathVariable long eventId,
                                  @RequestParam int rating) {
        log.info("Update event rating {}", rating);
        eventService.updateRatingEvent(eventId, rating);
    }
}
