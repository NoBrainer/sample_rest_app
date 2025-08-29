package com.example.sample_rest_app.service;

import com.example.sample_rest_app.model.Person;
import com.example.sample_rest_app.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonService {
    protected final PersonRepository repository;

    public Person create(Person entity) {
        return repository.save(entity);
    }

    public List<Person> getAll() {
        return repository.findAll();
    }

    public PageImpl<Person> getAll(Pageable pageable) {
        var page = repository.findAll(pageable);
        return new PageImpl<>(page.getContent(), pageable, page.getTotalElements());
    }
}
