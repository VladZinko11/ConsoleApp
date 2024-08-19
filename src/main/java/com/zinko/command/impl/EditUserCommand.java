package com.zinko.command.impl;

import com.zinko.command.Command;
import com.zinko.service.UserService;
import com.zinko.service.ValidationErrorService;
import com.zinko.service.dto.UserDto;
import com.zinko.service.exception.NumberFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;


@Component("edit")
@RequiredArgsConstructor
public class EditUserCommand implements Command {
    private final UserService userService;
    private final Validator validator;
    private final ValidationErrorService errorService;

    @Override
    public void handle(Scanner scanner) {
        System.out.println("Enter user id that you want to edit");
        long id;
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Wrong format id", e);
        }
        UserDto user = userService.getUserById(id);
        System.out.println("Enter new values or press Enter if you do not want to change them.");
        System.out.println("Enter new user name");
        String firstName = scanner.nextLine();
        if (!Objects.equals(firstName, "")) {
            user.setFirstName(firstName);
        }
        System.out.println("Enter new user last name");
        String lastName = scanner.nextLine();
        if (!Objects.equals(lastName, "")) {
            user.setLastName(lastName);
        }
        System.out.println("Enter new user email");
        String email = scanner.nextLine();
        if (!Objects.equals(email, "")) {
            user.setEmail(email);
        }
        System.out.println("Enter new user role (ADMIN, MANAGER, CUSTOMER)");
        String role = scanner.nextLine();
        if (!Objects.equals(role, "")) {
            user.setRole(role);
        }
        System.out.println("Enter new telephone numbers");
        List<String> numbers = new ArrayList<>();
        while (true) {
            String number = scanner.nextLine();
            if (Objects.equals(number, "")) {
                break;
            }
            numbers.add(number);
        }
        if (!numbers.isEmpty()) {
            user.setTelephoneNumbers(numbers);
        }
        Set<ConstraintViolation<UserDto>> violations = validator.validate(user);
        if (errorService.hasError(violations)) {
            errorService.getError(violations).forEach(System.out::println);
            handle(scanner);
        } else {
            userService.updateUser(user);
            System.out.println("User updated: ");
            writeUserInConsole(user);
        }
    }
}
