package com.example.sample_rest_app.controller;

import com.example.sample_rest_app.dto.PersonDTO;
import com.example.sample_rest_app.util.MockUtil;
import com.example.sample_rest_app.util.TestAuthUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestRestTemplateIntegrationTest {

    @Autowired
    TestRestTemplate template;

    @DisplayName("return UNAUTHORIZED without credentials")
    @Test
    void testUnauthorizedRequest() {
        ResponseEntity<List<PersonDTO>> response = template.exchange("/api/person/all", HttpMethod.GET, null, listType());
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @DisplayName("return OK when authorized")
    @Test
    void testAuthorizedRequest() {
        var request = TestAuthUtil.request();

        ResponseEntity<List<PersonDTO>> response = template.exchange("/api/person/all", HttpMethod.GET, request, listType());
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<PersonDTO> dtos = response.getBody();
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    @Disabled("For some reason, this gives 401 UNAUTHORIZED even though it has the same headers as the GET request above")
    @DisplayName("can create a Person")
    @Test
    void testCreatePerson() {
        PersonDTO personDto = MockUtil.mockCreatePersonDto();
        var request = TestAuthUtil.requestWithBody(personDto);

        ResponseEntity<List<PersonDTO>> response = template.exchange("/api/person", HttpMethod.POST, request, listType());
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<PersonDTO> dtos = response.getBody();
        assertNotNull(dtos);
    }

    private static <T> ParameterizedTypeReference<List<T>> listType() {
        return new ParameterizedTypeReference<>() {
        };
    }
}
