package com.zinko.service.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.regex.Pattern;

public class NumberValidator implements ConstraintValidator<TelephoneNumbers, List<String>> {
    private Pattern pattern;

    @Override
    public void initialize(TelephoneNumbers constraintAnnotation) {
        pattern = Pattern.compile(constraintAnnotation.regexp());
    }

    @Override
    public boolean isValid(List<String> list, ConstraintValidatorContext constraintValidatorContext) {
        if (list.size() > 3) {
            return false;
        }
        for (String number :
                list) {
            if (!pattern.matcher(number).matches()) {
                return false;
            }
        }
        return true;
    }
}
