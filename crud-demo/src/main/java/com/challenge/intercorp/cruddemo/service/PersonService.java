package com.challenge.intercorp.cruddemo.service;

import com.challenge.intercorp.cruddemo.model.Person;
import com.challenge.intercorp.cruddemo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class PersonService {


    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(String firstName, String lastName, String dni, Date birthDate) throws ParseException {
        return personRepository.save(new Person(firstName, lastName, dni, birthDate));
    }

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Person getByDni(String dni) {
        return personRepository.findByDni(dni);
    }

    public Person update(String firstName, String lastName, String dni, Date birthDate) {
        Person person = personRepository.findByDni(dni);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setBirthDate(birthDate);
        return personRepository.save(person);
    }

    public void deleteAll() {
        personRepository.deleteAll();
    }

    public void deleteByDni(String dni) {
        Person person = personRepository.findByDni(dni);
        personRepository.delete(person);
    }
}
