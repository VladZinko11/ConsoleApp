package com.zinko.data.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Builder
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private List<String> telephoneNumbers;

}
