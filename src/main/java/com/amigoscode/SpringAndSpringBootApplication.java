package com.amigoscode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public enum SortOrder {ASC, DESC}

    public record Person(String name, int id, int age, Gender gender){

    }

    public static List<Person> People = new ArrayList<>();

    static {
        People.add(new Person("James", 1, 23, Gender.MALE));
        People.add(new Person("Maria", 2, 20, Gender.FEMALE));
        People.add(new Person("John", 3, 27, Gender.MALE));
    }

    @GetMapping
    public List<Person> getPeople(@RequestParam(value = "sort", required = false, defaultValue = "asc") SortOrder sort,
                                  @RequestParam(value = "limit", required = false) Integer limit) {
        if (sort == SortOrder.DESC) {
            return People.stream().sorted(Comparator.comparing(Person::id).reversed()).limit(limit)
                    .collect(Collectors.toList());
        }
        return People.stream().sorted(Comparator.comparing(Person::id)).limit(limit)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public Optional<Person> getPersonById(@PathVariable("id") Integer id) {
        return People.stream()
                .filter(person -> person.id() == id)
                .findFirst();
    }
}
