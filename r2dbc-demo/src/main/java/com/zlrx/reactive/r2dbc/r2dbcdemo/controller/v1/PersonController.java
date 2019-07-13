package com.zlrx.reactive.r2dbc.r2dbcdemo.controller.v1;

import com.zlrx.reactive.r2dbc.r2dbcdemo.domain.Person;
import com.zlrx.reactive.r2dbc.r2dbcdemo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Person> findAll() {
        return personRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Person> findById(@PathVariable("id") Long id) {
        return personRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<Person> createPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

}
