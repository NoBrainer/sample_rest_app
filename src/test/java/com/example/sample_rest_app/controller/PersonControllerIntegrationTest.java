package com.example.sample_rest_app.controller;

import com.example.sample_rest_app.MockUtils;
import com.example.sample_rest_app.dto.PersonDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PersonControllerIntegrationTest {

    @Autowired
    PersonController controller;

    @DisplayName("create saves a Person to the database")
    @Test
    void testCreate() {
        var personDto = MockUtils.mockCreatePersonDto();

        var response = controller.create(personDto);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        PersonDTO body = response.getBody();
        assertNotNull(body);
        assertNotNull(body.getId());
    }
}
