package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.requests.RequestClient;
import ru.practicum.dto.requests.ParticipationRequestDto;
import ru.practicum.service.RequestService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users/")
@Validated
@Slf4j
@RequiredArgsConstructor
public class RequestPrivateController implements RequestClient {
    private final RequestService requestService;

    @GetMapping("/{userId}/requests")
    @Override
    public List<ParticipationRequestDto> findAllRequestsByUserId(@PathVariable Long userId) {
        log.info("findAllRequestsByUserId {}", userId);
        return requestService.findAllRequestsByUserId(userId);
    }

    @PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ParticipationRequestDto addRequest(@PathVariable Long userId,
                                              @RequestParam Long eventId) {
        log.info("addRequest {} for event {}", userId, eventId);
        return requestService.addRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    @Override
    public ParticipationRequestDto cancelRequest(@PathVariable Long userId,
                                                 @PathVariable Long requestId) {
        log.info("cancelRequest {} for request {}", userId, requestId);
        return requestService.cancelRequest(userId, requestId);
    }

    @GetMapping("/requests/events/{eventId}")
    @Override
    public List<ParticipationRequestDto> findAllRequestsByEventId(@PathVariable Long eventId) {
        log.info("findAllRequestsByEventId {}", eventId);
        return requestService.findAllRequestsByEventId(eventId);
    }

    @GetMapping("/requests/events/{eventId}/status")
    @Override
    public List<ParticipationRequestDto> findAllRequestsByEventIdAndStatus(@PathVariable Long eventId,
                                                                           @RequestParam String status) {
        log.info("findAllRequestsByEventIdAndStatus {}, {}", eventId, status);
        return requestService.findAllRequestsByEventIdAndStatus(eventId, status);
    }

    @GetMapping("/requests")
    @Override
    public List<ParticipationRequestDto> findAllRequestsByRequestsId(@RequestParam Set<Long> requestsId) {
        log.info("findAllRequestsByRequestsId {}", requestsId);
        return requestService.findAllRequestsByRequestsId(requestsId);
    }

    @PutMapping("/requests/status")
    @Override
    public List<ParticipationRequestDto> updateRequest(@RequestParam Set<Long> requestsId,
                                                       @RequestParam String status) {
        log.info("updateRequest {}", requestsId);
        return requestService.updateRequest(requestsId, status);
    }

    @GetMapping("/requests/existence")
    @Override
    public boolean findExistRequests(@RequestParam Long eventId,
                                     @RequestParam Long userId,
                                     @RequestParam String status) {
        log.info("findExistRequests {}", eventId);
        boolean exist = requestService.findExistRequests(eventId, userId, status);
        return exist;
    }
}
