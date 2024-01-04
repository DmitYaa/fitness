package kz.danilov.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.danilov.backend.Util;
import kz.danilov.backend.dto.PersonDTO;
import kz.danilov.backend.services.PeopleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * User: Nikolai Danilov
 * Date: 04.01.2024
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;
    private final PeopleService peopleService;

    @Autowired
    public AuthControllerTest(ObjectMapper objectMapper, MockMvc mockMvc, PeopleService peopleService) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
        this.peopleService = peopleService;
    }

    @AfterEach
    public void resetDb() {
        peopleService.deleteAll();
    }

    @Test
    public void postRegistrationThenGetJWTToken() throws Exception {
        PersonDTO personDTO = Util.createTestLoginPersonDTO();

        mockMvc.perform(post("/auth/registration/user")
                        .content(objectMapper.writeValueAsString(personDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("jwt").isNotEmpty());
    }
}
