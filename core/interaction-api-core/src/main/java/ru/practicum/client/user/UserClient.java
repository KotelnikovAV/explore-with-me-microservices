package ru.practicum.client.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.user.UserDto;
import ru.practicum.dto.user.UserRequestDto;
import ru.practicum.fallback.user.UserFallback;

import java.util.List;


@FeignClient(name = "user-server", fallback = UserFallback.class)
public interface UserClient {
    @GetMapping("/api/v1/admin/users")
    @Validated
    List<UserDto> getAllUsers(@RequestParam(required = false) List<Long> ids,
                              @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                              @RequestParam(defaultValue = "10") @Positive Integer size);

    @GetMapping("/api/v1/admin/users/rating")
    List<UserDto> getAllUsersBySortRating(@RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                          @RequestParam(defaultValue = "10") @Positive Integer size);

    @PostMapping("/api/v1/admin/users")
    @ResponseStatus(HttpStatus.CREATED)
    UserDto createUser(@RequestBody @Valid UserRequestDto userRequestDto);

    @DeleteMapping("/api/v1/admin/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable long userId);

    @GetMapping("/api/v1/admin/users/{userId}/existence")
    boolean getUserExists(@PathVariable long userId);

    @PutMapping("/api/v1/admin/users/{userId}")
    void updateRatingUser(@PathVariable long userId,
                          @RequestParam int rating);
}
