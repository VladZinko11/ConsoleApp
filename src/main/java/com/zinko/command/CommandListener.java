package com.zinko.command;

import com.zinko.service.exception.BadCredentialsException;
import com.zinko.service.exception.ConsoleAppException;
import com.zinko.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandListener {

    private final CommandFactory commandFactory;

    public void ListenCommand() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Enter command:
                create - to add new user
                edit - to edit user
                get all - to get all users
                get by id - to find user by id
                get by email - to find user by email
                delete - to delete user by id
                exit - to finish and close program""");

        while (true) {
            String command = scanner.nextLine();
            try {
                Command commandInstance = commandFactory.getCommand(command);
                if(commandInstance==null) {
                    System.out.println("Not found command " + command);
                    continue;
                }
                commandInstance.handle(scanner);
            }
            catch (NotFoundException | BadCredentialsException | NumberFormatException e) {
                System.out.println(e.getMessage());
            } catch (ConsoleAppException e) {
                System.out.println(e.getMessage());
                log.error(e.getMessage(), e);
            } catch (Exception e) {
                System.out.println("Oops, something wrong");
                log.error(e.getMessage(), e);
            }
        }
    }
}
