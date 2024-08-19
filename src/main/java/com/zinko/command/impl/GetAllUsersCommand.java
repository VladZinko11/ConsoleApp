package com.zinko.command.impl;

import com.zinko.command.Command;
import com.zinko.service.UserService;
import com.zinko.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component("get all")
@RequiredArgsConstructor
@Slf4j
public class GetAllUsersCommand implements Command {
    private final UserService userService;

    @Override
    public void handle(Scanner scanner) {
        List<UserDto> users = userService.getAllUsers();
        for (UserDto user : users) {
            writeUserInConsole(user);
        }
    }


}
