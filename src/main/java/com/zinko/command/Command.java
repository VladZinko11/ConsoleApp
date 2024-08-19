package com.zinko.command;

import com.zinko.service.dto.UserDto;

import java.util.Scanner;

public interface Command {
    void handle(Scanner scanner);

    default void writeUserInConsole(UserDto user) {
        StringBuilder str = new StringBuilder();
        str.append(user.getId()).append(" | ")
                .append(user.getFirstName()).append(" | ")
                .append(user.getLastName()).append(" | ")
                .append(user.getEmail()).append(" | ")
                .append(user.getRole()).append(" | ");
        user.getTelephoneNumbers().forEach(n -> str.append(n).append(", "));
        System.out.println(str);
    }
}
