package com.zinko.service.impl;

import com.zinko.service.ValidationErrorService;
import com.zinko.service.dto.UserDto;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

@Service
public class ValidationErrorServiceImpl implements ValidationErrorService {

    @Override
    public boolean hasError(Set<ConstraintViolation<UserDto>> violations) {
        return !violations.isEmpty();
    }

    @Override
    public List<String> getError(Set<ConstraintViolation<UserDto>> violations) {
        List<String> errorMessages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        return errorMessages;
    }
}
