package ru.practicum.events.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.stats.avro.ActionTypeAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.ewm.stats.proto.ActionTypeProto;
import ru.practicum.ewm.stats.proto.UserActionProto;

import java.time.Instant;

@UtilityClass
public class AvroMapper {

    public UserActionAvro convertToAvro(UserActionProto userActionProto) {
        return UserActionAvro.newBuilder()
                .setUserId(userActionProto.getUserId())
                .setEventId(userActionProto.getEventId())
                .setActionType(getActionTypeAvro(userActionProto.getActionType()))
                .setTimestamp(getInstant(userActionProto.getTimestamp()))
                .build();
    }

    private ActionTypeAvro getActionTypeAvro(ActionTypeProto actionTypeProto) {
        return switch (actionTypeProto) {
            case ACTION_VIEW -> ActionTypeAvro.VIEW;
            case ACTION_REGISTER -> ActionTypeAvro.REGISTER;
            case ACTION_LIKE -> ActionTypeAvro.LIKE;
            default -> null;
        };
    }

    private Instant getInstant(com.google.protobuf.Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
    }
}
