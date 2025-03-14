package com.amigoscode.Person;


import com.amigoscode.validation.Foo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewPersonRequest(
        @NotEmpty(message = "name should not be empty") @Foo String name,  //@Foo is a custom annotation
        @Min(value = 18, message = "Age must be greater than 18") Integer age,
        @NotNull(message = "Gender cannot be null") Gender gender,
        @NotEmpty(message = "Email must not be empty") @Email(message = "Enter a correct email") String email
){}
