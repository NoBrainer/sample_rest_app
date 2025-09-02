package com.example.sample_rest_app.controller;

import com.example.sample_rest_app.MockUtils;
import com.example.sample_rest_app.dto.PersonDTO;
import com.example.sample_rest_app.model.Person;
import com.example.sample_rest_app.repository.PersonRepository;
import com.example.sample_rest_app.service.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PersonControllerIntegrationTest {

    @Autowired
    PersonController controller;

    @Autowired
    PersonRepository repository;

    @DisplayName("create returns a saved Person with a uuid")
    @Test
    void testCreate() {
        var personDto = MockUtils.mockCreatePersonDto();

        var response = controller.create(personDto);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        PersonDTO body = response.getBody();
        assertNotNull(body);
        assertNotNull(body.getUuid());
    }

    @DisplayName("create fails when the request includes a uuid")
    @Test
    void testCreateWithId() {
        var personDtoWithId = MockUtils.mockCreatePersonDto().toBuilder().uuid(UUID.randomUUID()).build();

        //TODO: Figure out how to test this through GlobalRestControllerAdvice
        var exception = assertThrows(ResponseStatusException.class, () -> controller.create(personDtoWithId));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals(PersonService.ERROR_PERSON_UUID_ALREADY_SET, exception.getReason());
    }

    @DisplayName("getAll returns an empty list when nothing is saved")
    @Test
    void testGetAllNoResults() {
        ResponseEntity<List<PersonDTO>> response = controller.getAll();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @DisplayName("getAll returns all people")
    @Test
    void testGetAllWithResults() {
        List<Person> savedPeople = repository.findAll();

        ResponseEntity<List<PersonDTO>> response = controller.getAll();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<PersonDTO> dtos = response.getBody();
        assertNotNull(dtos);
        assertEquals(savedPeople.size(), dtos.size());
        savedPeople.forEach(entity -> assertTrue(dtos.stream().anyMatch(dto -> entity.getFirstName().equals(dto.getFirstName()))));
    }

    @DisplayName("getAll paged returns all people")
    @Test
    void testGetAllWithPagedResults() {
        var savedPeople = repository.findAll();
        var pageable = Pageable.ofSize(10);

        ResponseEntity<PageImpl<PersonDTO>> response = controller.getAll(pageable);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        PageImpl<PersonDTO> page = response.getBody();
        assertNotNull(page);
        assertEquals(savedPeople.size(), page.getTotalElements());

        List<PersonDTO> dtos = page.getContent();
        assertNotNull(dtos);
        assertEquals(savedPeople.size(), dtos.size());
        savedPeople.forEach(entity -> assertTrue(dtos.stream().anyMatch(dto -> entity.getFirstName().equals(dto.getFirstName()))));
    }
}
