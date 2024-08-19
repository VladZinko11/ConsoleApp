package com.zinko.service.impl;

import com.zinko.data.UserRepository;
import com.zinko.data.entity.User;
import com.zinko.service.UserService;
import com.zinko.service.UserServiceMapper;
import com.zinko.service.dto.UserDto;
import com.zinko.service.exception.BadCredentialsException;
import com.zinko.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserServiceMapper mapper;

    @Override
    public UserDto saveUser(UserDto userDto) {
        isEmailValid(userDto);
        User savedUser = userRepository.saveUser(mapper.toEntity(userDto));
        return mapper.toDto(savedUser);
    }

    private void isEmailValid(UserDto userDto) {
        Optional<User> optionalUser = userRepository.getUserByEmail(userDto.getEmail());
        if (optionalUser.isPresent()) {
            throw new BadCredentialsException("User with email " + userDto.getEmail() + " is already exist");
        }
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> optionalUser = userRepository.getUserById(id);
        User user = optionalUser.orElseThrow(() -> new NotFoundException("Not found user with id " + id));
        return mapper.toDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.getUserByEmail(email);
        User user = optionalUser.orElseThrow(() -> new NotFoundException("Not found user with email " + email));
        return mapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.getAllUsers();
        return users.stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public boolean updateUser(UserDto userDto) {
        isEmailValid(userDto);
        return userRepository.updateUser(mapper.toEntity(userDto));
    }

    @Override
    public void deleteUser(Long id) {
        boolean deleted = userRepository.deleteUserById(id);
        if (!deleted) {
            throw new NotFoundException("Not found user with id " + id);
        }
    }
}
