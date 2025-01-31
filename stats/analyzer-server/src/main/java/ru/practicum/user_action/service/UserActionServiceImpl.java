package ru.practicum.user_action.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.user_action.mapper.UserActionMapper;
import ru.practicum.user_action.model.UserAction;
import ru.practicum.user_action.model.UserActionId;
import ru.practicum.user_action.repository.UserActionRepository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

        Map<UserActionId, UserAction> userActions = userActionMapper
                .listUserActionAvroToListUserAction(userActionsAvro).stream()
                .collect(Collectors.toMap(UserAction::getUserActionId, Function.identity()));

        List<UserAction> userActionsFromDb = userActionRepository.findAllById(userActions.keySet());

        if (userActionsFromDb.size() != userActionsAvro.size()) {
            List<UserAction> newUserActions = userActions.values().stream()
                    .filter(userAction -> !userActionsFromDb.contains(userAction))
                    .toList();
            userActionRepository.saveAll(newUserActions);
        }

        userActionsFromDb.forEach(userAction -> userAction.setActionType(userActions
                .get(userAction.getUserActionId()).getActionType()));

        log.info("Saved user actions to database");
    }
}
