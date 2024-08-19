package com.zinko.command.impl;

import com.zinko.command.Command;
import com.zinko.service.UserService;
import com.zinko.service.exception.NumberFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("delete")
@RequiredArgsConstructor
public class DeleteUserCommand implements Command {
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
        userService.deleteUser(id);
        System.out.println("User with id " + id + " deleted");
    }
}
