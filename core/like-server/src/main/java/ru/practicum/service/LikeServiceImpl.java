package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.UserActionClient;
import ru.practicum.client.event.EventClient;
import ru.practicum.client.requests.RequestClient;
import ru.practicum.client.user.UserClient;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.enums.Status;
import ru.practicum.enums.StatusLike;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.RestrictionsViolationException;
import ru.practicum.model.Like;
import ru.practicum.repository.LikeRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static ru.practicum.utility.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final UserClient userClient;
    private final EventClient eventClient;
    private final RequestClient requestClient;
    private final UserActionClient userActionClient;

    @Override
    @Transactional
    public EventFullDto addLike(long eventId, long userId, StatusLike statusLike) {
        log.info("The beginning of the process of adding like to an event");

        if (!userClient.getUserExists(userId)) {
            throw new NotFoundException("User with id=" + userId + " was not found");
        }

        EventFullDto event = eventClient.findEventById(eventId);

        if (!requestClient.findExistRequests(eventId, userId, Status.CONFIRMED.name())) {
            throw new RestrictionsViolationException("In order to like, you must be a participant in the event");
        }

        if (likeRepository.existsByEventIdAndUserId(eventId, userId)) {
            throw new RestrictionsViolationException("You have already rated this event");
        }

        if (event.getInitiator().getId() == userId) {
            throw new RestrictionsViolationException("The initiator of the event cannot rate himself");
        }

        Like like = new Like();
        like.setUserId(userId);
        like.setEventId(event.getId());
        like.setStatus(statusLike);
        like.setCreated(LocalDateTime.now());
        likeRepository.save(like);

//        userActionClient.collectUserAction(eventId, userId, ActionTypeProto.ACTION_LIKE);

        return changeRatingUserAndEvent(event, statusLike, DIFFERENCE_RATING_BY_ADD);
    }

    @Override
    @Transactional
    public EventFullDto updateLike(long eventId, long userId, StatusLike statusLike) {
        log.info("The beginning of the process of updating like to an event");

        if (!userClient.getUserExists(userId)) {
            throw new NotFoundException("User with id=" + userId + " was not found");
        }

        EventFullDto event = eventClient.findEventById(eventId);

        Optional<Like> likeOptional = likeRepository.findByEventIdAndUserId(eventId, userId);

        if (likeOptional.isPresent()) {
            Like like = likeOptional.get();
            if (like.getStatus() == statusLike) {
                throw new RestrictionsViolationException("You have already " + statusLike + " this event");
            }
            like.setStatus(statusLike);
            like.setCreated(LocalDateTime.now());
            return changeRatingUserAndEvent(event, statusLike, DIFFERENCE_RATING_BY_UPDATE);
        } else {
            throw new NotFoundException("You didn't rate this event");
        }
    }

    @Override
    @Transactional
    public void deleteLike(long eventId, long userId) {
        log.info("The beginning of the process of deleting like to an event");

        if (!userClient.getUserExists(userId)) {
            throw new NotFoundException("User with id=" + userId + " was not found");
        }

        EventFullDto event = eventClient.findEventById(eventId);

        Optional<Like> likeOptional = likeRepository.findByEventIdAndUserId(eventId, userId);

        if (likeOptional.isPresent()) {
            Like like = likeOptional.get();
            StatusLike statusLike = like.getStatus();
            likeRepository.delete(like);
            changeRatingUserAndEvent(event, statusLike, DIFFERENCE_RATING_BY_DELETE);
        } else {
            throw new RestrictionsViolationException("You haven't reacted it yet");
        }
        log.info("The reaction was deleted");
    }

    private EventFullDto changeRatingUserAndEvent(EventFullDto event, StatusLike statusLike, int difference) {
        if (statusLike == StatusLike.LIKE) {
            userClient.updateRatingUser(event.getInitiator().getId(), difference);
            eventClient.updateRatingEvent(event.getId(), difference);
            event.setLikes(event.getLikes() + difference);
            return event;
        } else if (statusLike == StatusLike.DISLIKE) {
            userClient.updateRatingUser(event.getInitiator().getId(), difference * -1);
            eventClient.updateRatingEvent(event.getId(), difference * -1);
            event.setLikes(event.getLikes() - difference);
            return event;
        }
        return null;
    }
}
