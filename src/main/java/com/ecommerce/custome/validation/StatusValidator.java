package com.ecommerce.custome.validation;

import com.ecommerce.model.Status;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusValidator implements ConstraintValidator<StatusValid, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Status[] status= Status.values();
		for(Status s:status)
		{
			if(s.toString().equals(value))
			{
				return true;
			}
		}
		
		return false;
	}
}
