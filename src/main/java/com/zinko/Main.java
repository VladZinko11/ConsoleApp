package com.zinko;

import com.zinko.command.CommandListener;


public class Main {

    public static void main(String[] args) {
        CommandListener commandListener = new CommandListener();
        commandListener.handleCommand();
    }
}