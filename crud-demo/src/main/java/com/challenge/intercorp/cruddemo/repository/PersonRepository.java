package com.challenge.intercorp.cruddemo.repository;

import com.challenge.intercorp.cruddemo.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {

    public Person findByDni(String dni);

}
