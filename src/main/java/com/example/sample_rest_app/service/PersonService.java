package com.example.sample_rest_app.service;

import com.example.sample_rest_app.model.Person;
import com.example.sample_rest_app.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonService {
    public static final String ERROR_PERSON_UUID_ALREADY_SET = "Cannot create a Person with 'uuid' already set";

    protected final PersonRepository repository;

    @Transactional
    public Person create(Person entity) {
        if (entity.getUuid() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_PERSON_UUID_ALREADY_SET);
        }
        return repository.save(entity);
    }

    @Transactional
    public List<Person> getAll() {
        return repository.findAll();
    }

    @Transactional
    public PageImpl<Person> getAll(Pageable pageable) {
        var page = repository.findAll(pageable);
        return new PageImpl<>(page.getContent(), pageable, page.getTotalElements());
    }
}
