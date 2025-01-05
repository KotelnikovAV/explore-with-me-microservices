package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Like;
import ru.practicum.model.event.Event;
import ru.practicum.model.user.User;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByEventAndUser(Event event, User user);

    Optional<Like> findByEventAndUser(Event event, User user);
}
