package ru.practicum.client.requests;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.requests.ParticipationRequestDto;
import ru.practicum.fallback.requests.RequestFallback;

import java.util.List;


@FeignClient(name = "requests", fallback = RequestFallback.class)
public interface RequestClient {
    @GetMapping("/api/v1/users/{userId}/requests")
    List<ParticipationRequestDto> getAllRequests(@PathVariable Long userId);

    @PostMapping("/api/v1/users/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    ParticipationRequestDto addRequest(@PathVariable Long userId,
                                       @RequestParam Long eventId);

    @PatchMapping("/api/v1/users/{userId}/requests/{requestId}/cancel")
    ParticipationRequestDto cancelRequest(@PathVariable Long userId,
                                          @PathVariable Long requestId);
}
