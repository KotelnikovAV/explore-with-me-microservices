package ru.practicum.service;


import ru.practicum.dto.event.EventFullDto;
import ru.practicum.enums.StatusLike;

public interface LikeService {
    EventFullDto addLike(long eventId, long userId, StatusLike statusLike);

    EventFullDto updateLike(long eventId, long userId, StatusLike statusLike);

    void deleteLike(long eventId, long userId);
}
