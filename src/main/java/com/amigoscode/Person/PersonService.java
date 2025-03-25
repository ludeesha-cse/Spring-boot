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
    private final FakePersonRepository fakePersonRepository;
    private final PersonRepository personRepository;

    public PersonService(FakePersonRepository fakePersonRepository, PersonRepository personRepository) {
        this.fakePersonRepository = fakePersonRepository;
        this.personRepository = personRepository;
    }

    public List<Person> getPeople(SortingOrder sort,
                                  Integer limit) {
        return personRepository.findAll();
    }

    public Person getPersonById(Integer id) {
        return fakePersonRepository.getPeople().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Person with id: " + id + " does not exists"));

    }

    public void deletePersonById(Integer id) {
        Person person = fakePersonRepository.getPeople().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Person with id: " + id + " does not exists"));
        fakePersonRepository.getPeople().remove(person);
    }

    public void addPerson(NewPersonRequest person) {
        boolean exists = fakePersonRepository.getPeople().stream()
                .anyMatch(p -> p.getEmail().equalsIgnoreCase(person.email()));
        if (exists) {
            throw new DuplicateResourceException("email taken");
        }
        fakePersonRepository.getPeople().add(new Person(
                fakePersonRepository.getId().incrementAndGet(),
                person.name(),
                person.age(),
                person.gender(),
                person.email())
        );
    }

    public void updatePerson(Integer id, PersonUpdateReq request) {

        fakePersonRepository.getPeople().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(p -> {
                    int index = fakePersonRepository.getPeople().indexOf(p); // Get the index before updating

                    Person updatedPerson = p; // Default to the existing person

                    if (request.name() != null && !request.name().isEmpty() && !request.name().equals(p.getName())) {
                        updatedPerson = new Person(p.getId(), request.name(), p.getAge(), p.getGender(), p.getEmail());
                    }
                    if (request.age() != null && !request.age().equals(p.getAge())) {
                        updatedPerson = new Person(p.getId(), p.getName(), request.age(), p.getGender(), p.getEmail());
                    }
                    if (request.email() != null && !request.email().isEmpty() && !request.email().equals(p.getEmail())) {
                        boolean exists = fakePersonRepository.getPeople().stream()
                                .anyMatch(person -> person.getEmail().equalsIgnoreCase(request.email()));
                        if (exists) {
                            throw new DuplicateResourceException("email taken");
                        }
                        updatedPerson = new Person(p.getId(), p.getName(), p.getAge(), p.getGender(), request.email());
                    }

                    // Update the person in the list only if changed
                    if (!updatedPerson.equals(p)) {
                        fakePersonRepository.getPeople().set(index, updatedPerson);
                    }

                    return updatedPerson;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Person with id: " + id + " does not exist"));
    }


}