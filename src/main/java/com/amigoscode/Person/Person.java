package com.amigoscode.Person;

public record Person(
        Integer id,
        String name,
        Integer age,
        Gender gender,
        String email
){}
