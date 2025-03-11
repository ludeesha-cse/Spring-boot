package com.amigoscode.Person;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewPersonRequest(
        @NotEmpty String name,
        @Min(18) Integer age,
        @NotBlank @NotNull Gender gender
){}
