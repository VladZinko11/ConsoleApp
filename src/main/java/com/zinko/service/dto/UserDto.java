package com.zinko.service.dto;

import com.zinko.service.validation.TelephoneNumbers;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@Builder
public class UserDto {
    private Long id;
    @NotBlank(message = "enter user name")
    private String firstName;
    @NotBlank(message = "enter user last name")
    private String lastName;
    @Email(regexp = ".+[@].+[\\.].+", message = "wrong email format")
    @NotBlank(message = "enter user email")
    private String email;
    @Pattern(regexp = "^(ADMIN|MANAGER|CUSTOMER)$", message = "role must be one of {ADMIN, MANAGER, CUSTOMER}")
    private String role;
    @TelephoneNumbers(regexp = "^375\\d{9}$", size = 3, message = "must be no more than 3 numbers, format 375XXXXXXXXX")
    private List<String> telephoneNumbers;

}
