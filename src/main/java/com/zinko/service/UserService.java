package com.zinko.service;

import com.zinko.service.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto saveUser(UserDto userDto);

    UserDto getUserById(Long id);

    UserDto getUserByEmail(String email);

    List<UserDto> getAllUsers();

    boolean updateUser(UserDto userDto);

    void deleteUser(Long id);

}
