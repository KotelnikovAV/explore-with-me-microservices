package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.practicum.exception.IntegrityViolationException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserRequestDto;
import ru.practicum.user.dto.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers(List<Long> ids, int from, int size) {
        log.info("The beginning of the process of finding all users");
        PageRequest pageRequest = PageRequest.of(from, size, Sort.by(Sort.Direction.ASC, "id"));
        List<User> users;

        if (CollectionUtils.isEmpty(ids)) {
            users = userRepository.findAll(pageRequest).getContent();
        } else {
            users = userRepository.findAllByIdIn(ids, pageRequest).getContent();
        }

        log.info("The user has been found");
        return userMapper.listUserToListUserDto(users);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsersBySortRating(int from, int size) {
        log.info("The beginning of the process of finding all users by sort rating");
        PageRequest pageRequest = PageRequest.of(from, size, Sort.by(Sort.Direction.DESC, "rating"));
        List<User> users = userRepository.findAll(pageRequest).getContent();
        log.info("The user by sort rating has been found");
        return userMapper.listUserToListUserDto(users);
    }

    @Override
    public UserDto createUser(UserRequestDto requestDto) {
        log.info("The beginning of the process of creating a user");
        User user = userMapper.userRequestDtoToUser(requestDto);
        userRepository.findUserByEmail(user.getEmail()).ifPresent(u -> {
            throw new IntegrityViolationException("User with email " + u.getEmail() + " already exists");
        });
        user.setRating(0L);
        userRepository.save(user);
        log.info("The user has been created");
        return userMapper.userToUserDto(user);
    }

    @Override
    public void deleteUser(long userId) {
        log.info("The beginning of the process of deleting a user");
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                "User with id = " + userId + " not found"));
        userRepository.deleteById(userId);
        log.info("The user has been deleted");
    }
}