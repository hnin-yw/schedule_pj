package com.example.schedule.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.schedule.entity.Group;

public class GroupValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Group.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	Group group = (Group) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "groupName", "NotEmpty");
    }
}