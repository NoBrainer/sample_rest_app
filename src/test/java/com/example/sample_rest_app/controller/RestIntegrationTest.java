package com.example.sample_rest_app.controller;

import com.example.sample_rest_app.dto.PersonDTO;
import com.example.sample_rest_app.util.MockUtil;
import com.example.sample_rest_app.util.TestAuthUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.util.List;

import static com.example.sample_rest_app.util.ParamUtil.listOfPeopleType;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestIntegrationTest {

    @Autowired
    TestRestTemplate template;

    @Autowired
    MockMvc mockMvc;

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
        assertTrue(dtos.isEmpty());
    }

    /**
     * This example is worse than the TestRestTemplate example above for several reasons:
     * - It throws an Exception.
     * - It requires manual JSON parsing.
     * - It requires username and password constants.
     * - It is less readable.
     */
    @WithMockUser(username = "user", password = "c30ebd45-5e81-464b-8eeb-bef9b71404da")
    @DisplayName("return OK when authorized [MockMvc]")
    @Test
    void testAuthorizedRequestAlternative() throws Exception {
        var result = mockMvc.perform(MockMvcRequestBuilders.get(URI.create("/api/person/all")))
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(result);

        var response = result.getResponse();
        assertNotNull(response);
        String jsonString = response.getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, PersonDTO.class);
        List<PersonDTO> dtos = objectMapper.readValue(jsonString, collectionType);
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    @Disabled("For some reason, this gives 401 UNAUTHORIZED even though it has the same headers as the GET request above")
    @DisplayName("can create a Person")
    @Test
    void testCreatePerson() {
        PersonDTO personDto = MockUtil.mockCreatePersonDto();
        var request = TestAuthUtil.requestWithBody(personDto);

        ResponseEntity<List<PersonDTO>> response = template.exchange("/api/person", HttpMethod.POST, request, listOfPeopleType());
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<PersonDTO> dtos = response.getBody();
        assertNotNull(dtos);
    }
}
