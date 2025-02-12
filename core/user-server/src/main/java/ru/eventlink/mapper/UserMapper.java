package ru.eventlink.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.eventlink.dto.user.UserDto;
import ru.eventlink.dto.user.UserRequestDto;
import ru.eventlink.model.User;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDto userToUserDto(User user);

    User userRequestDtoToUser(UserRequestDto userRequestDto);

    List<UserDto> listUserToListUserDto(List<User> users);
}
