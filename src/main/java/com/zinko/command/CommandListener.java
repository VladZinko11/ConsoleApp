package com.zinko.command;

import com.zinko.ConsoleAppContext;
import com.zinko.service.exception.BadCredentialsException;
import com.zinko.service.exception.ConsoleAppException;
import com.zinko.service.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class CommandListener {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsoleAppContext.class);

    public void handleCommand() {
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
            if (Objects.equals(command, "exit")) {
                return;
            }
            try {
                Command commandInstance = context.getBean(command, Command.class);
                commandInstance.handle(scanner);
            } catch (NoSuchBeanDefinitionException e) {
                System.out.println("Not found command " + command);
                log.error("Not found command {}", command, e);
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
