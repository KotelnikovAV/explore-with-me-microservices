package ru.practicum.fallback.user;

import org.springframework.stereotype.Component;
import ru.practicum.client.user.UserClient;
import ru.practicum.dto.user.UserDto;
import ru.practicum.dto.user.UserRequestDto;
import ru.practicum.exception.ServerUnavailableException;

import java.util.List;

@Component
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

    @Override
    public boolean getUserExists(long userId) {
        throw new ServerUnavailableException("Endpoint /api/v1/admin/users/{userId}/existence method GET is unavailable");
    }

    @Override
    public void updateRatingUser(long userId, int rating) {
        throw new ServerUnavailableException("Endpoint /api/v1/admin/users/{userId} method PATCH is unavailable");
    }
}
