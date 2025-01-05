package ru.practicum.service;

import ru.practicum.dto.user.UserDto;
import ru.practicum.dto.user.UserRequestDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers(List<Long> ids, int from, int size);

    List<UserDto> getAllUsersBySortRating(int from, int size);

    UserDto createUser(UserRequestDto requestDto);

    void deleteUser(long userId);
}
