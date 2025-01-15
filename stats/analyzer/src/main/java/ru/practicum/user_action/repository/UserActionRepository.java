package ru.practicum.user_action.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.user_action.model.UserAction;

public interface UserActionRepository extends JpaRepository<UserAction, Long> {

}