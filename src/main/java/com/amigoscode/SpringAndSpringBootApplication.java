package com.amigoscode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@SpringBootApplication
public class SpringAndSpringBootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(
                SpringAndSpringBootApplication.class,
                args
        );
    }

    public enum Gender {MALE, FEMALE}

    public record Person(String name, int id, int age, Gender gender){

    }

    public static List<Person> People = new ArrayList<>();

    static {
        People.add(new Person("James", 1, 23, Gender.MALE));
        People.add(new Person("Maria", 2, 20, Gender.FEMALE));
        People.add(new Person("John", 3, 27, Gender.MALE));
    }

    @GetMapping
    public List<Person> getPeople() {
        return People;
    }
}
