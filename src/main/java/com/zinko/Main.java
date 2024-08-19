package com.zinko;

import com.zinko.command.CommandListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsoleAppContext.class);
        CommandListener commandListener = context.getBean("commandListener", CommandListener.class);
        commandListener.ListenCommand();
    }
}