package com.amigoscode.Person;

import com.amigoscode.SortingOrder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/people")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    @GetMapping
    public List<Person> getPeople(@RequestParam(value = "sort", required = false, defaultValue = "ASC") SortingOrder sort,
                                  @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        return personService.getPeople(sort, limit);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Person>> getPersonById(@PathVariable("id") Integer id) {
        Optional<Person> person = personService.getPersonById(id);
        return ResponseEntity.ok().body(person);
    }

    @DeleteMapping("{id}")
    public void deletePersonById(@Valid @Positive @PathVariable("id") Integer id) {
        personService.deletePersonById(id);
    }

    @PostMapping
    public void addPerson(@Valid @RequestBody NewPersonRequest person) {
        personService.addPerson(person);
    }

    @PutMapping("{id}")
    public void updatePerson(@PathVariable("id") Integer id, @RequestBody PersonUpdateReq request) {
        personService.updatePerson(id, request);
    }

}
