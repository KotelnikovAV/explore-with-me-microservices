package ru.practicum.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.StatClient;
import ru.practicum.config.AppConfig;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.enums.EventPublicSort;
import ru.practicum.event.service.EventService;
import ru.practicum.utility.Constants;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@Slf4j
@Validated
@RequiredArgsConstructor
public class EventPublicController {
    private final EventService eventService;
    private final StatClient statClient;
    private final AppConfig appConfig;

    @GetMapping
    public List<EventShortDto> getAllPublicEvents(@RequestParam(required = false) String text,
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
                                                  HttpServletRequest request) {
        log.info("Get all public events by text {}", text);
        List<EventShortDto> allPublicEvents = eventService.getAllPublicEvents(text, categories, paid,
                rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        statClient.saveHit(appConfig.getAppName(), request);
        return allPublicEvents;
    }

    @GetMapping("/{id}")
    public EventFullDto getPublicEventById(@PathVariable Long id, HttpServletRequest request) {
        log.info("Get public event by id {}", id);
        EventFullDto publicEventById = eventService.getPublicEventById(id);
        statClient.saveHit(appConfig.getAppName(), request);
        return publicEventById;
    }
}
