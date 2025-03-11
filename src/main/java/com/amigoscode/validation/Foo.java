package com.amigoscode.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FooValidator.class)
public @interface Foo {
    String message() default "This is not a valid foo";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
