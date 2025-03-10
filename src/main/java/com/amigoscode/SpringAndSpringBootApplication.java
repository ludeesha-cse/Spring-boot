
package com.amigoscode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

    public enum Gender {MALE, FEMALE}

    public enum SortOrder {ASC, DESC}

    private static final AtomicInteger id = new AtomicInteger(0);

//    public record Person(Integer id, String name, Integer age, Gender gender){}

    public static class Person {
        private final Integer id;
        private final String name;
        private final Integer age;
        private final Gender gender;

        public Person(Integer id, String name, Integer age, Gender gender) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public Integer getId() {
            return id;
        }

        @JsonIgnore
        public String getPassword() {
            return "password";
        }

        public Integer getAge() {
            return age;
        }

        public Gender getGender() {
            return gender;
        }

        public String getProfiloos(){
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

        @Override
        public int hashCode() {
            return Objects.hash(id, name, age, gender);
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
    public List<Person> getPeople(@RequestParam(value = "sort", required = false, defaultValue = "ASC") SortOrder sort,
                                  @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        if (sort == SortOrder.DESC) {
            return People.stream().sorted(Comparator.comparing(Person::getId).reversed()).limit(limit)
                    .collect(Collectors.toList());
        }
        return People.stream().sorted(Comparator.comparing(Person::getId)).limit(limit)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Person>> getPersonById(@PathVariable("id") Integer id) {
        Optional<Person> person = People.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        return ResponseEntity.ok().body(person);
    }

    @DeleteMapping("{id}")
    public void deletePersonById(@PathVariable("id") Integer id) {
        People.removeIf(person -> person.getId().equals(id));
    }

    @PostMapping
    public void addPerson(@RequestBody Person person) {
        People.add(new Person(
                id.incrementAndGet(),
                person.getName(),
                person.getAge(),
                person.getGender())
        );
    }

    @PutMapping("{id}")
    public void updatePerson(@PathVariable("id") Integer id, @RequestBody PersonUpdateRequest request) {
        People.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .ifPresent(p -> {
                    if (request.name() != null && !request.name().isEmpty() && !request.name().equals(p.getName())) {
                        Person person = new Person(p.getId(), request.name(), p.getAge(), p.getGender());
                        People.set(People.indexOf(p), person);
                    }
                    if (request.age() != null && !request.age().equals(p.getAge()) ) {
                        Person person = new Person(p.getId(), p.getName(), request.age(), p.getGender());
                        People.set(People.indexOf(p), person);
                    }
                });
    }

}
