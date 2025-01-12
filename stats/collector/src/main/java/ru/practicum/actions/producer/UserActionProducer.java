package ru.practicum.actions.producer;

import ru.practicum.ewm.stats.proto.UserActionProto;

public interface UserActionProducer {
    void collectUserAction(UserActionProto userActionProto);
}
