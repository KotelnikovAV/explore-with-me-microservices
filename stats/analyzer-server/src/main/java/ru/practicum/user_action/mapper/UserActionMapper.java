package ru.practicum.user_action.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.user_action.model.UserAction;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserActionMapper {
    @Mapping(target = "actionDate", expression = "java(getLocalDateTime())")
    UserAction userActionAvroToUserAction(UserActionAvro userActionAvro);

    default LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    List<UserAction> listUserActionAvroToListUserAction(List<UserActionAvro> userActionAvro);
}
