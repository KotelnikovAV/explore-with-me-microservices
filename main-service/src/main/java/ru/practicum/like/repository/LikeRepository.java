package ru.practicum.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.model.Event;
import ru.practicum.like.model.Like;
import ru.practicum.user.model.User;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByEventAndUser(Event event, User user);

    Optional<Like> findByEventAndUser(Event event, User user);
}
