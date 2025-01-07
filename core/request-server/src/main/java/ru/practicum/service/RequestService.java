package ru.practicum.service;

import ru.practicum.dto.requests.ParticipationRequestDto;

import java.util.List;
import java.util.Set;

public interface RequestService {

    List<ParticipationRequestDto> findAllRequestsByUserId(long userId);

    ParticipationRequestDto addRequest(long userId, long eventId);

    ParticipationRequestDto cancelRequest(long userId, long requestId);

    List<ParticipationRequestDto> findAllRequestsByEventId(Long eventId);

    List<ParticipationRequestDto> findAllRequestsByRequestsId(Set<Long> requestsId);

    List<ParticipationRequestDto> updateRequest(Set<Long> requestsId, String status);

    boolean findExistRequests(Long eventId, Long userId, String status);

    List<ParticipationRequestDto> findAllRequestsByEventIdAndStatus(Long eventId, String status);
}
