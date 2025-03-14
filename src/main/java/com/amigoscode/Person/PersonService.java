package com.amigoscode.Person;

import com.amigoscode.SortingOrder;
import com.amigoscode.exception.DuplicateResourceException;
import com.amigoscode.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
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

    public Person getPersonById(Integer id) {
        return personRepository.getPeople().stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Person with id: " + id + " does not exists"));

    }

    public void deletePersonById(Integer id) {
        Person person = personRepository.getPeople().stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Person with id: " + id + " does not exists"));
        personRepository.getPeople().remove(person);
    }

    public void addPerson(NewPersonRequest person) {
        boolean exists = personRepository.getPeople().stream()
                .anyMatch(p -> p.email().equalsIgnoreCase(person.email()));
        if (exists) {
            throw new DuplicateResourceException("email taken");
        }
        personRepository.getPeople().add(new Person(
                personRepository.getId().incrementAndGet(),
                person.name(),
                person.age(),
                person.gender(),
                person.email())
        );
    }

    public void updatePerson(Integer id, PersonUpdateReq request) {

        personRepository.getPeople().stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .map(p -> {
                    int index = personRepository.getPeople().indexOf(p); // Get the index before updating

                    Person updatedPerson = p; // Default to the existing person

                    if (request.name() != null && !request.name().isEmpty() && !request.name().equals(p.name())) {
                        updatedPerson = new Person(p.id(), request.name(), p.age(), p.gender(), p.email());
                    }
                    if (request.age() != null && !request.age().equals(p.age())) {
                        updatedPerson = new Person(p.id(), p.name(), request.age(), p.gender(), p.email());
                    }
                    if (request.email() != null && !request.email().isEmpty() && !request.email().equals(p.email())) {
                        boolean exists = personRepository.getPeople().stream()
                                .anyMatch(person -> person.email().equalsIgnoreCase(request.email()));
                        if (exists) {
                            throw new DuplicateResourceException("email taken");
                        }
                        updatedPerson = new Person(p.id(), p.name(), p.age(), p.gender(), request.email());
                    }

                    // Update the person in the list only if changed
                    if (!updatedPerson.equals(p)) {
                        personRepository.getPeople().set(index, updatedPerson);
                    }

                    return updatedPerson;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Person with id: " + id + " does not exist"));
    }


}