package com.zinko.data.impl;

import com.zinko.data.UserRepositoryMapper;
import com.zinko.data.entity.Role;
import com.zinko.data.entity.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserRepositoryMapperImpl implements UserRepositoryMapper {

    @Override
    public User mapToUser(String str) {
        String[] fields = str.split(" \\| ");
        Long id = Long.parseLong(fields[0]);
        String firstName = fields[1];
        String lastName = fields[2];
        String email = fields[3];
        Role role = Role.valueOf(fields[4]);
        String[] split = fields[5].split(", ");
        List<String> numbers;
        if (split.length < 2) {
            numbers = List.of(split[0].substring(0, 12));
        } else {
            numbers = Arrays.asList(split);
        }
        return User.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .role(role)
                .telephoneNumbers(numbers)
                .build();
    }

    @Override
    public String mapToString(User user) {
        StringBuilder str = new StringBuilder();
        str.append(user.getId()).append(" | ")
                .append(user.getFirstName()).append(" | ")
                .append(user.getLastName()).append(" | ")
                .append(user.getEmail()).append(" | ")
                .append(user.getRole().toString()).append(" | ");
        user.getTelephoneNumbers().forEach(n -> str.append(n).append(", "));
        str.append("\n");
        return String.valueOf(str);
    }
}
