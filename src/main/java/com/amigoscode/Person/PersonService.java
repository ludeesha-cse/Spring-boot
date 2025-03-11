package com.amigoscode.Person;

import com.amigoscode.SortingOrder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getPeople(SortingOrder sort,
                                  Integer limit) {
        if (sort == SortingOrder.DESC) {
            return personRepository.getPeople().stream().sorted(Comparator.comparing(Person::id).reversed()).limit(limit)
                    .collect(Collectors.toList());
        }
        return personRepository.getPeople().stream().sorted(Comparator.comparing(Person::id)).limit(limit)
                .collect(Collectors.toList());
    }

    public Optional<Person> getPersonById(Integer id) {
        return personRepository.getPeople().stream()
                .filter(p -> p.id().equals(id))
                .findFirst();
    }

    public void deletePersonById(Integer id) {
        personRepository.getPeople().removeIf(person -> person.id().equals(id));
    }

    public void addPerson(NewPersonRequest person) {
        personRepository.getPeople().add(new Person(
                personRepository.getId().incrementAndGet(),
                person.name(),
                person.age(),
                person.gender())
        );
    }

    public void updatePerson(Integer id, PersonUpdateReq request) {
        personRepository.getPeople().stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .ifPresent(p -> {
                    if (request.name() != null && !request.name().isEmpty() && !request.name().equals(p.name())) {
                        Person person = new Person(p.id(), request.name(), p.age(), p.gender());
                        personRepository.getPeople().set(personRepository.getPeople().indexOf(p), person);
                    }
                    if (request.age() != null && !request.age().equals(p.age()) ) {
                        Person person = new Person(p.id(), p.name(), request.age(), p.gender());
                        personRepository.getPeople().set(personRepository.getPeople().indexOf(p), person);
                    }
                });
    }

}
