package com.zinko.command.impl;

import com.zinko.command.Command;
import com.zinko.service.UserService;
import com.zinko.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("get by email")
@RequiredArgsConstructor
public class GetUserByEmailCommand implements Command {
    private final UserService userService;

    @Override
    public void handle(Scanner scanner) {
        System.out.println("Enter user email");
        String email = scanner.nextLine();
        UserDto user = userService.getUserByEmail(email);
        writeUserInConsole(user);
    }
}
