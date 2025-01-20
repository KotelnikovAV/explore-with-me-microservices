package ru.practicum.user_action.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.user_action.mapper.UserActionMapper;
import ru.practicum.user_action.model.UserAction;
import ru.practicum.user_action.repository.UserActionRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserActionServiceImpl implements UserActionService {
    private final UserActionRepository userActionRepository;
    private final UserActionMapper userActionMapper;

    @Override
    @Transactional
    public void saveUserActions(List<UserActionAvro> userActionsAvro) {
        log.info("Saving user actions to database");

        List<UserAction> userActions = userActionMapper.listUserActionAvroToListUserAction(userActionsAvro);
        userActionRepository.saveAll(userActions);
        log.info("Saved user actions to database");
    }
}
