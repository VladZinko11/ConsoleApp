package com.zinko.command.impl;

import com.zinko.command.Command;
import com.zinko.service.UserService;
import com.zinko.service.ValidationErrorService;
import com.zinko.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

@Component("create")
@RequiredArgsConstructor
public class CreateUserCommand implements Command {
    private final UserService userService;
    private final ValidationErrorService errorService;
    private final Validator validator;


    @Override
    public void handle(Scanner scanner) {
        UserDto.UserDtoBuilder builder = UserDto.builder();
        System.out.println("Enter user name");
        builder.firstName(scanner.nextLine());
        System.out.println("Enter user last name");
        builder.lastName(scanner.nextLine());
        System.out.println("Enter user email");
        builder.email(scanner.nextLine());
        System.out.println("Enter user role (ADMIN, MANAGER, CUSTOMER)");
        builder.role(scanner.nextLine());
        System.out.println("Enter telephone numbers");
        List<String> numbers = new ArrayList<>();
        while (true) {
            String number = scanner.nextLine();
            if (Objects.equals(number, "")) {
                break;
            }
            numbers.add(number);
        }
        builder.telephoneNumbers(numbers);
        UserDto userDto = builder.build();
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        if (errorService.hasError(violations)) {
            errorService.getError(violations).forEach(System.out::println);
            handle(scanner);
        } else {
            UserDto savedUser = userService.saveUser(userDto);
            writeUserInConsole(savedUser);
        }

    }
}
