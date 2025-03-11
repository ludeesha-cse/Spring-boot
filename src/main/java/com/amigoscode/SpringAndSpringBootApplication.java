
package com.amigoscode;

import com.amigoscode.Person.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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

    @Bean
    CommandLineRunner commandLineRunner(ObjectMapper objectMapper) throws JsonProcessingException {
        String personString = "{\"id\":1,\"name\":\"John\",\"age\":27,\"gender\":\"MALE\"}";
        Person person = objectMapper.readValue(personString, Person.class);

        System.out.println(person);
        System.out.println(objectMapper.writeValueAsString(person));
        return args -> {
        };
    }

    private static final AtomicInteger id = new AtomicInteger(0);

//    public record Person(Integer id, String name, Integer age, Gender gender){}

    public record Person(Integer id, String name, Integer age, Gender gender) {

        @JsonIgnore
        public String getPassword() {
            return "password";
        }

        public String getProfils() {
            return name + " " + age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", gender=" + gender +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(age, person.age) && gender == person.gender;
        }

    }

    public record PersonUpdateRequest(String name, Integer age){}

    public static List<Person> People = new ArrayList<>();

    static {
        People.add(new Person(id.incrementAndGet(), "James", 23, Gender.MALE));
        People.add(new Person( id.incrementAndGet(), "Maria",20, Gender.FEMALE));
        People.add(new Person(id.incrementAndGet(), "John", 27, Gender.MALE));
    }

    @GetMapping
    public List<Person> getPeople(@RequestParam(value = "sort", required = false, defaultValue = "ASC") SortingOrder sort,
                                  @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        if (sort == SortingOrder.DESC) {
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
                        Person person = new Person(p.id(), request.name(), p.age(), p.gender());
                        People.set(People.indexOf(p), person);
                    }
                    if (request.age() != null && !request.age().equals(p.age()) ) {
                        Person person = new Person(p.id(), p.name(), request.age(), p.gender());
                        People.set(People.indexOf(p), person);
                    }
                });
    }

}
