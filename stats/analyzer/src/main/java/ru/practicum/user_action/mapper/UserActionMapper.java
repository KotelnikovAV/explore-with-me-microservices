package ru.practicum.user_action.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.user_action.model.UserAction;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserActionMapper {
    UserAction userActionAvroToUserAction(UserActionAvro userActionAvro);

    List<UserAction> listUserActionAvroToListUserAction(List<UserActionAvro> userActionAvro);
}
