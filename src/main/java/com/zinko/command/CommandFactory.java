package com.zinko.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommandFactory {

    private final Map<String, Command> commands;

    public Command getCommand(String command) {
        return commands.get(command);
    }
}
