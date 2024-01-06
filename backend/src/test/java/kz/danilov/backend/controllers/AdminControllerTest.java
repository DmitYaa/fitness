package kz.danilov.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.danilov.backend.Utils;
import kz.danilov.backend.models.Person;
import kz.danilov.backend.security.JWTUtil;
import kz.danilov.backend.services.PeopleService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * User: Nikolai Danilov
 * Date: 06.01.2024
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdminControllerTest {
    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;
    private final PeopleService peopleService;
    private final JWTUtil jwtUtil;

    @Autowired
    public AdminControllerTest(ObjectMapper objectMapper,
                               MockMvc mockMvc,
                               PeopleService peopleService,
                               JWTUtil jwtUtil) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
        this.peopleService = peopleService;
        this.jwtUtil = jwtUtil;
    }

    private Person personAdmin = null;
    private String adminToken = null;

    @BeforeAll
    public void initialize() {
        resetDb();
        personAdmin = peopleService.save(Utils.personAdmin);
        adminToken = jwtUtil.generateToken(personAdmin.getName());
    }

    @AfterAll
    public void after() {
        resetDb();
    }

    private void resetDb() {
        peopleService.deleteAll();
    }

    @Test
    void getPerson() throws Exception {
        getPersonThenCheckHis();
    }

    @Test
    void getAllPeople() {
    }

    @Test
    void getSearchPeople() {
    }

    @Test
    void getPeopleSize() {
    }

    @Test
    void setNewPersonData() {
    }

    private void getPersonThenCheckHis() throws Exception {
        String data = mockMvc.perform(get("/auth/get_person_data")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("person_data").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println(data);
        assertTrue(data.contains(personAdmin.getName()));
    }
}
