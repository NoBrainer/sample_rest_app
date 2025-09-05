package com.example.sample_rest_app;

import com.example.sample_rest_app.controller.PersonController;
import com.example.sample_rest_app.repository.PersonRepository;
import com.example.sample_rest_app.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class ExampleApplicationTest {

    @Autowired
    PersonController controller;

    @Autowired
    PersonService service;

    @Autowired
    PersonRepository repository;

    @Test
    void contextLoads() {
        assertNotNull(controller);
        assertNotNull(service);
        assertNotNull(repository);
    }
}
