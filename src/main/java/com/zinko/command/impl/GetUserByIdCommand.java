package com.zinko.command.impl;

import com.zinko.command.Command;
import com.zinko.service.UserService;
import com.zinko.service.dto.UserDto;
import com.zinko.service.exception.NumberFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("get by id")
@RequiredArgsConstructor
public class GetUserByIdCommand implements Command {
    private final UserService userService;

    @Override
    public void handle(Scanner scanner) {
        System.out.println("Enter user id");
        long id;
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Wrong format id", e);
        }
        UserDto user = userService.getUserById(id);
        writeUserInConsole(user);
    }
}
