package com.example.sample_rest_app.controller;

import com.example.sample_rest_app.dto.PersonDTO;
import com.example.sample_rest_app.util.TestAuthUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.sample_rest_app.util.ParamUtil.listOfPeopleType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestIntegrationTest {

    @Autowired
    TestRestTemplate template;

    @DisplayName("return UNAUTHORIZED without credentials")
    @Test
    void testUnauthorized() {
        ResponseEntity<List<PersonDTO>> response = template.exchange("/api/person/all", HttpMethod.GET, null, listOfPeopleType());
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @DisplayName("return OK when authorized")
    @Test
    void testAuthorizedRequest() {
        var request = TestAuthUtil.request();

        ResponseEntity<List<PersonDTO>> response = template.exchange("/api/person/all", HttpMethod.GET, request, listOfPeopleType());
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<PersonDTO> dtos = response.getBody();
        assertNotNull(dtos);
    }
}
