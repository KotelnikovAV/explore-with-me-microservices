package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Like;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByEventIdAndUserId(Long eventId, Long userId);

    Optional<Like> findByEventIdAndUserId(Long eventId, Long userId);
}
