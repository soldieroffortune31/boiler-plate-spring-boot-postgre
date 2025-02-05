package com.example.springpostgres.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BooleanValueValidator implements ConstraintValidator<BooleanValue, Boolean> {

    @Override
    public boolean isValid(Boolean value, ConstraintValidatorContext context) {
        return value != null;
    }
    
}
