package ru.practicum.events.producer;


import ru.practicum.ewm.stats.proto.UserActionProto;

public interface EventsProducer {
    void collectUserAction(UserActionProto userActionProto);
}
