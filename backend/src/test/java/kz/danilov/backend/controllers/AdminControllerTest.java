package kz.danilov.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.danilov.backend.Utils;
import kz.danilov.backend.dto.SearchDTO;
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
        getPersonWithIdThenCheckHis(peopleService.findAll().get(0));
        getNonExistentPerson();
    }

    @Test
    void getPeople() throws Exception {
        getPeopleThenCheckIt(peopleService.findAll().get(0));
    }

    @Test
    void getSearchPeople() throws Exception {
        getPeopleThenCheckContains(peopleService.findAll().get(0));
    }

    @Test
    void getPeopleSize() {
    }

    @Test
    void setNewPersonData() {
    }

    private void getPersonWithIdThenCheckHis(Person person) throws Exception {
        String data = Utils.getResultActionsWithTokenButWithoutBody(mockMvc, "/admin/person/" + person.getId(), adminToken)
                .andExpect(jsonPath("name").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(data.contains(person.getName()) && data.contains(person.getRole()));
    }

    private void getNonExistentPerson() throws Exception {
        String data = Utils.getResultActionsWithTokenButWithoutBody(mockMvc, "/admin/person/" + Integer.MAX_VALUE, adminToken)
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(data.contains("\"message\":\"Person with this id wasn't found!\""));
    }

    private void getPeopleThenCheckIt(Person person) throws Exception {
        String data = Utils.getResultActionsWithTokenButWithoutBody(mockMvc, "/admin/people", adminToken)
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(data.contains(person.getName()) && data.contains(person.getRole()));
    }

    private void getPeopleThenCheckContains(Person person) throws Exception {
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchString(person.getName().substring(1));
        String data = Utils.getResultActionsWithTokenAndBody(mockMvc,
                "/admin/search_people",
                adminToken, objectMapper, searchDTO)
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(data.contains(person.getName()));
    }
}
