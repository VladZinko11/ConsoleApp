package com.zinko.data.impl;

import com.zinko.data.UserIdGenerator;
import com.zinko.data.UserRepository;
import com.zinko.data.UserRepositoryMapper;
import com.zinko.data.entity.User;
import com.zinko.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    public static final String I_O_EXCEPTION_OCCURRED = "I/O exception occurred";
    public static final String NOT_FOUND_FILE = "Not found file ";
    public static final String SERVER_ERROR_MESSAGE = "Oops, something wrong";
    private final UserRepositoryMapper userMapper;
    @Value("${file.name.basic}")
    private String basicFileName;
    @Value("${file.name.supporting}")
    private String supportingFileName;


    @Override
    public User saveUser(User user) {
        user.setId(UserIdGenerator.getNewUserId());
        try (FileWriter fileWriter = new FileWriter(basicFileName, true);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            String userLine = userMapper.mapToString(user);
            writer.write(userLine);
            writer.flush();
        } catch (IOException e) {
            throw new ServerException(I_O_EXCEPTION_OCCURRED, e);
        }
        return user;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        checkFile();
        try (FileReader fileReader = new FileReader(basicFileName);
             BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("")) {
                    continue;
                }
                if (line.startsWith(id + " |")) {
                    User user = userMapper.mapToUser(line);
                    return Optional.of(user);
                }
            }
        } catch (FileNotFoundException e) {
            throw new ServerException(NOT_FOUND_FILE + basicFileName, e);
        } catch (IOException e) {
            throw new ServerException(I_O_EXCEPTION_OCCURRED, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        checkFile();
        try (FileReader fileReader = new FileReader(basicFileName);
             BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("")) {
                    continue;
                }
                if (line.contains("| " + email + " |")) {
                    User user = userMapper.mapToUser(line);
                    return Optional.of(user);
                }
            }
        } catch (FileNotFoundException e) {
            throw new ServerException(NOT_FOUND_FILE + basicFileName, e);
        } catch (IOException e) {
            throw new ServerException(I_O_EXCEPTION_OCCURRED, e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        checkFile();
        List<String> users = new ArrayList<>();
        try (FileReader fileReader = new FileReader(basicFileName);
             BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("")) {
                    continue;
                }
                users.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new ServerException(NOT_FOUND_FILE + basicFileName, e);
        } catch (IOException e) {
            throw new ServerException(I_O_EXCEPTION_OCCURRED, e);
        }
        return users.stream()
                .map(userMapper::mapToUser)
                .toList();
    }

    @Override
    public boolean updateUser(User user) {
        boolean updated = false;
        try (FileReader fileReader = new FileReader(basicFileName);
             BufferedReader reader = new BufferedReader(fileReader);
             FileWriter fileWriter = new FileWriter(supportingFileName);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("")) {
                    continue;
                }
                if (line.startsWith(user.getId() + " |")) {
                    line = userMapper.mapToString(user);
                    updated = true;
                }
                writer.write(line + "\n");
            }
        } catch (FileNotFoundException e) {
            throw new ServerException(NOT_FOUND_FILE + basicFileName, e);
        } catch (IOException e) {
            throw new ServerException(I_O_EXCEPTION_OCCURRED, e);
        }
        changeFiles();
        return updated;
    }

    @Override
    public boolean deleteUserById(Long id) {
        boolean deleted = false;
        try (FileReader fileReader = new FileReader(basicFileName);
             BufferedReader reader = new BufferedReader(fileReader);
             FileWriter fileWriter = new FileWriter(supportingFileName);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("")) {
                    continue;
                }
                if (line.startsWith(id + " |")) {
                    deleted = true;
                    continue;
                }
                writer.write(line + "\n");
            }
        } catch (FileNotFoundException e) {
            throw new ServerException(NOT_FOUND_FILE + basicFileName, e);
        } catch (IOException e) {
            throw new ServerException(I_O_EXCEPTION_OCCURRED, e);
        }
        changeFiles();
        return deleted;
    }

    private void changeFiles() {
        File file = new File(basicFileName);
        if (!file.delete()) {
            throw new ServerException(SERVER_ERROR_MESSAGE);
        }
        File newFile = new File(supportingFileName);
        if (!newFile.renameTo(new File(basicFileName))) {
            throw new ServerException(SERVER_ERROR_MESSAGE);
        }
    }

    private void checkFile() {
        File file = new File(basicFileName);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new ServerException(SERVER_ERROR_MESSAGE);
            }
        }
    }
}
