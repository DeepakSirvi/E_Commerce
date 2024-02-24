package com.ecommerce.custome.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidater.class)
public @interface ImageNameValid {
	
	String message() default "invalid image name";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
// Use @ImageNameValid or a String where we can check this is blank or not
