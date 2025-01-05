package ru.practicum.fallback.user;

import ru.practicum.client.user.UserClient;
import ru.practicum.dto.user.UserDto;
import ru.practicum.dto.user.UserRequestDto;
import ru.practicum.exception.ServerUnavailableException;

import java.util.List;

public class UserFallback implements UserClient {
    @Override
    public List<UserDto> getAllUsers(List<Long> ids, Integer from, Integer size) {
        throw new ServerUnavailableException("Endpoint /api/v1/admin/users method GET is unavailable");
    }

    @Override
    public List<UserDto> getAllUsersBySortRating(Integer from, Integer size) {
        throw new ServerUnavailableException("Endpoint /api/v1/admin/users/rating method GET is unavailable");
    }

    @Override
    public UserDto createUser(UserRequestDto userRequestDto) {
        throw new ServerUnavailableException("Endpoint /api/v1/admin/users method POST is unavailable");
    }

    @Override
    public void deleteUser(long userId) {
        throw new ServerUnavailableException("Endpoint /api/v1/admin/users/{userId} method DELETE is unavailable");
    }
}
