package ru.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryWithQuerydsl extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    Page<User> findAllByIdIn(List<Long> ids, PageRequest pageRequest);
}
