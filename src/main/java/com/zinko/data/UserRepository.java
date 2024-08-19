package com.zinko.data;

import com.zinko.data.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User saveUser(User user);

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    List<User> getAllUsers();

    boolean updateUser(User user);

    boolean deleteUserById(Long id);

}
