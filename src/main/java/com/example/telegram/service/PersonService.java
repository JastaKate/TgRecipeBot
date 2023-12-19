package com.example.telegram.service;

import com.example.telegram.entity.Person;
import com.example.telegram.repository.PersonRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepo personRepo;

    public PersonService(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    public List<Person> getPersons() {
        return personRepo.findAll();
    }
}
