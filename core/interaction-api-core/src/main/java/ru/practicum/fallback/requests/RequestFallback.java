package ru.practicum.fallback.requests;

import ru.practicum.client.requests.RequestClient;
import ru.practicum.dto.requests.ParticipationRequestDto;
import ru.practicum.exception.ServerUnavailableException;

import java.util.List;

public class RequestFallback implements RequestClient {
    @Override
    public List<ParticipationRequestDto> getAllRequests(Long userId) {
        throw new ServerUnavailableException("Endpoint /api/v1/users/{userId}/requests method GET is unavailable");
    }

    @Override
    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        throw new ServerUnavailableException("Endpoint /api/v1/users/{userId}/requests method POST is unavailable");
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        throw new ServerUnavailableException("Endpoint /api/v1/users/{userId}/requests/{requestId}/cancel method PATCH " +
                "is unavailable");
    }
}
