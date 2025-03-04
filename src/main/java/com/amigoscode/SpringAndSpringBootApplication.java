package com.amigoscode;

import jdk.jfr.Frequency;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@SpringBootApplication
public class SpringAndSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                SpringAndSpringBootApplication.class,
                args
        );
    }

    public enum Gender {MALE, FEMALE}

    public enum SortOrder {ASC, DESC}

    private static final AtomicInteger id = new AtomicInteger(0);

    public record Person(Integer id, String name, Integer age, Gender gender){

    }

    public record PersonUpdateRequest(String name, Integer age){}

    public static List<Person> People = new ArrayList<>();

    static {
        People.add(new Person(id.incrementAndGet(), "James", 23, Gender.MALE));
        People.add(new Person( id.incrementAndGet(), "Maria",20, Gender.FEMALE));
        People.add(new Person(id.incrementAndGet(), "John", 27, Gender.MALE));
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
    public ResponseEntity<Optional<Person>> getPersonById(@PathVariable("id") Integer id) {
        Optional<Person> person = People.stream()
                .filter(p -> p.id().equals(id))
                .findFirst();
        return ResponseEntity.ok().body(person);
    }

    @DeleteMapping("{id}")
    public void deletePersonById(@PathVariable("id") Integer id) {
        People.removeIf(person -> person.id().equals(id));
    }

    @PostMapping
    public void addPerson(@RequestBody Person person) {
        People.add(new Person(
                id.incrementAndGet(),
                person.name(),
                person.age(),
                person.gender())
        );
    }

    @PutMapping("{id}")
    public void updatePerson(@PathVariable("id") Integer id, @RequestBody PersonUpdateRequest request) {
        People.stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .ifPresent(p -> {
                    if (request.name() != null && !request.name().isEmpty() && !request.name().equals(p.name())) {
                        Person person = new Person(p.id, request.name(), p.age(), p.gender());
                        People.set(People.indexOf(p), person);
                    }
                    if (request.age() != null && !request.age().equals(p.age()) ) {
                        Person person = new Person(p.id(), p.name(), request.age(), p.gender());
                        People.set(People.indexOf(p), person);
                    }
                });
    }

}
