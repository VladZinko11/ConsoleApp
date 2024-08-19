package com.zinko.command.impl;

import com.zinko.command.Command;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("exit")
public class ExitCommand implements Command {
    @Override
    public void handle(Scanner scanner) {
        System.exit(0);
    }
}
