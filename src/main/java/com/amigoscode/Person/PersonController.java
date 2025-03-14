package com.amigoscode.Person;

import com.amigoscode.SortingOrder;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/people")
public class PersonController {

    private final PersonService personService;
    private final Validator validator;

    public PersonController(PersonService personService,
                            Validator validator) {
        this.personService = personService;
        this.validator = validator;
    }

    @GetMapping
    public List<Person> getPeople(@RequestParam(value = "sort", required = false, defaultValue = "ASC") SortingOrder sort,
                                  @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        return personService.getPeople(sort, limit);
    }

    @GetMapping("{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable("id") Integer id) {
        Person person = personService.getPersonById(id);
        return ResponseEntity.ok().body(person);
    }

    @DeleteMapping("{id}")
    public void deletePersonById(
            @Valid @Positive @PathVariable("id") Integer id) {
        personService.deletePersonById(id);
    }

    @PostMapping
    public void addPerson(@Valid @RequestBody NewPersonRequest person) {
        /*
            Set<ConstraintViolation<NewPersonRequest>> validate =
                    validator.validate(person);
            validate.forEach(error -> System.out.println(error.getMessage()));
            if(!validate.isEmpty()) {
                throw new ConstraintViolationException(validate);
            }
        */
        personService.addPerson(person);
    }

    @PutMapping("{id}")
    public void updatePerson(@Valid @Positive @PathVariable("id") Integer id,
                             @RequestBody PersonUpdateReq request) {
        personService.updatePerson(id, request);
    }

}
