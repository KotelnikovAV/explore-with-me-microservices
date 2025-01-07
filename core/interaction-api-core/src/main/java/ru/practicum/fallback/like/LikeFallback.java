package ru.practicum.fallback.like;

import org.springframework.stereotype.Component;
import ru.practicum.client.like.LikeClient;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.enums.StatusLike;
import ru.practicum.exception.ServerUnavailableException;

@Component
public class LikeFallback implements LikeClient {
    @Override
    public EventFullDto addLike(long eventId, long userId, StatusLike reaction) {
        throw new ServerUnavailableException("Endpoint /api/v1/event/{eventId}/like/{userId} method POST is unavailable");
    }

    @Override
    public EventFullDto updateLike(long eventId, long userId, StatusLike reaction) {
        throw new ServerUnavailableException("Endpoint /api/v1/event/{eventId}/like/{userId} method PATCH is unavailable");
    }

    @Override
    public void deleteLike(long eventId, long userId) {
        throw new ServerUnavailableException("Endpoint /api/v1/event/{eventId}/like/{userId} method DELETE is unavailable");
    }
}
