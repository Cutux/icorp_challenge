package com.challenge.intercorp.cruddemo.controller;


import com.challenge.intercorp.cruddemo.model.Person;
import com.challenge.intercorp.cruddemo.repository.PersonRepository;
import com.challenge.intercorp.cruddemo.service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value="/cruddemo/v1")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/create")
    public String create(@RequestBody Person person) throws ParseException, JsonProcessingException {
        person = personService.createPerson(person.getFirstName(), person.getLastName(), person.getDni(), person.getBirthDate());
        return objectMapper.writeValueAsString(person);
    }

    @GetMapping("/get/{person-dni}")
    public Person getPerson(@PathVariable(value="person-dni") String dni) {
        return personService.getByDni(dni);
    }

    @GetMapping("/getAll")
    public List<Person> getAll() {
        return personService.getAll();
    }

    @PutMapping("/update")
    public String update(@RequestBody Person person) throws JsonProcessingException {
        person = personService.update(person.getFirstName(), person.getLastName(), person.getDni(), person.getBirthDate());
        return objectMapper.writeValueAsString(person);
    }

    @DeleteMapping("/delete/{person-dni}")
    public String delete(@PathVariable(value="person-dni") String dni) {
        personService.deleteByDni(dni);
        return "Se eliminó a la persona con DNI: " + dni;
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll() {
        personService.deleteAll();
        return "Se eliminaron a todas las personas.";
    }
}
