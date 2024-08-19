package com.zinko;


import com.zinko.command.Command;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan
@PropertySource("classpath:/application.properties")
public class ConsoleAppContext {

    @Bean
    public ValidatorFactory validatorFactory() {
        return Validation.buildDefaultValidatorFactory();
    }

    @Bean
    public Validator validator(ValidatorFactory validatorFactory) {
        return validatorFactory.getValidator();
    }

    @Bean
    public Map<String, Command> commands() {
        return new HashMap<>();
    }
}
