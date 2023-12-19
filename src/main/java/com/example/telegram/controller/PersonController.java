package com.example.telegram.controller;

import com.example.telegram.entity.Person;
import com.example.telegram.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bot")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/users")
    public ResponseEntity<List<Person>> getPersons() {
        return new ResponseEntity<>(personService.getPersons(), HttpStatus.OK);
    }

}
