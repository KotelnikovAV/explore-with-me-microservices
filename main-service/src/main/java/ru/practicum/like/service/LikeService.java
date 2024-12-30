package ru.practicum.like.service;

import ru.practicum.event.dto.EventFullDto;
import ru.practicum.like.model.StatusLike;

public interface LikeService {
    EventFullDto addLike(long eventId, long userId, StatusLike statusLike);

    EventFullDto updateLike(long eventId, long userId, StatusLike statusLike);

    void deleteLike(long eventId, long userId);
}
