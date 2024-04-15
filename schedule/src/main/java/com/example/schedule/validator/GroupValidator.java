package com.example.schedule.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.schedule.entity.*;

public class GroupValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Group.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");
	}
}