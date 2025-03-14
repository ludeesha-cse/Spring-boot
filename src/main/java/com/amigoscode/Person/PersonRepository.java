package com.amigoscode.Person;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PersonRepository {
    private final AtomicInteger id = new AtomicInteger(0);

    private final List<Person> People = new ArrayList<>();

    {
        People.add(new Person(id.incrementAndGet(), "James", 23, Gender.MALE, "james@email.com"));
        People.add(new Person( id.incrementAndGet(), "Maria",20, Gender.FEMALE, "maria@email.com"));
        People.add(new Person(id.incrementAndGet(), "John", 27, Gender.MALE, "john@email.com"));
    }

    public AtomicInteger getId(){
        return id;
    }

    public List<Person> getPeople(){
        return People;
    }
}
