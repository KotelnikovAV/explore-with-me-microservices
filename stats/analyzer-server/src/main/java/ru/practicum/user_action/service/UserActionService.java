package ru.practicum.user_action.service;

import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.util.List;

public interface UserActionService {
    void saveUserActions(List<UserActionAvro> userActionsAvro);
}
