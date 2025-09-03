package com.example.sample_rest_app.controller;

import com.example.sample_rest_app.dto.PersonDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MockMvcIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @DisplayName("return UNAUTHORIZED without credentials")
    @Test
    void testUnauthorizedRequest() throws Exception {
        var result = mockMvc.perform(MockMvcRequestBuilders.get(URI.create("/api/person/all")))
                .andExpect(status().isUnauthorized())
                .andReturn();
        assertNotNull(result);
    }

    /**
     * This example is worse than the TestRestTemplate example for several reasons:
     * - It throws an Exception.
     * - It requires manual JSON parsing.
     * - It requires username and password as constants.
     * - It is less readable.
     * - It takes seconds instead of milliseconds.
     */
    @WithMockUser(username = "user", password = "c30ebd45-5e81-464b-8eeb-bef9b71404da")
    @DisplayName("return OK when authorized")
    @Test
    void testAuthorizedRequest() throws Exception {
        var result = mockMvc.perform(MockMvcRequestBuilders.get(URI.create("/api/person/all")))
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(result);

        var response = result.getResponse();
        assertNotNull(response);
        String json = response.getContentAsString();

        List<PersonDTO> dtos = parseListOfPeopleDtos(json);
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    private static List<PersonDTO> parseListOfPeopleDtos(String json) throws JsonProcessingException {
        CollectionType collectionType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, PersonDTO.class);
        return OBJECT_MAPPER.readValue(json, collectionType);
    }
}
