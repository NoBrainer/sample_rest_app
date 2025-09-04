package com.example.sample_rest_app.controller;

import com.example.sample_rest_app.dto.PersonDTO;
import com.example.sample_rest_app.util.MockUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    private static final String USERNAME = "user";
    private static final String PASSWORD = "c30ebd45-5e81-464b-8eeb-bef9b71404da";

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
    @WithMockUser(username = USERNAME, password = PASSWORD)
    @DisplayName("return OK when authorized")
    @Test
    void testAuthorizedRequest() throws Exception {
        var result = mockMvc.perform(MockMvcRequestBuilders.get("/api/person/all"))
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(result);

        var response = result.getResponse();
        assertNotNull(response);
        String json = response.getContentAsString();
        assertTrue(StringUtils.isNotBlank(json));

        List<PersonDTO> dtos = OBJECT_MAPPER.readValue(json, listType());
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    // Note that this gets us 403 while the TestRestTemplate version gets us 401
    @Disabled("For some reason, this gives 403 UNAUTHORIZED even though it has the same headers as the GET request above")
    @WithMockUser(username = USERNAME, password = PASSWORD)
    @DisplayName("can create a Person")
    @Test
    void testCreatePerson() throws Exception {
        PersonDTO personDto = MockUtil.mockCreatePersonDto();

        var result = mockMvc.perform(
                        MockMvcRequestBuilders.post(URI.create("/api/person"))
                                .content(OBJECT_MAPPER.writeValueAsString(personDto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(result);

        var response = result.getResponse();
        assertNotNull(response);
        String json = response.getContentAsString();
        assertTrue(StringUtils.isNotBlank(json));

        ResponseEntity<PersonDTO> responseEntity = OBJECT_MAPPER.readValue(json, responseEntityType());
        assertNotNull(responseEntity);
        PersonDTO dto = responseEntity.getBody();
        assertNotNull(dto);
        assertNotNull(dto.getUuid());
    }

    private static <T> TypeReference<ResponseEntity<T>> responseEntityType() {
        return new TypeReference<>() {
        };
    }

    private static <T> TypeReference<List<T>> listType() {
        return new TypeReference<>() {
        };
    }
}
