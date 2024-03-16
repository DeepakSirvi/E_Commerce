package com.ecommerce.custome.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = StatusValidator.class)
public @interface StatusValid {
	
	String message() default "Invalid Status";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
