package com.amigoscode.Person;


import com.amigoscode.validation.Foo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewPersonRequest(
        @NotEmpty @Foo String name,  //@Foo is a custom annotation
        @Min(value = 18, message = "Age must be greater than 20") Integer age,
        @NotNull Gender gender
){}
