package com.zinko.service;

import com.zinko.service.dto.UserDto;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

public interface ValidationErrorService {

    List<String> getError(Set<ConstraintViolation<UserDto>> violations);

    boolean hasError(Set<ConstraintViolation<UserDto>> violations);
}
