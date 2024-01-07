package kz.danilov.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.danilov.backend.Utils;
import kz.danilov.backend.dto.PersonDataDTO;
import kz.danilov.backend.dto.SearchDTO;
import kz.danilov.backend.models.Person;
import kz.danilov.backend.security.JWTUtil;
import kz.danilov.backend.services.PeopleService;
import kz.danilov.backend.util.ModelMapperUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private final ModelMapperUtil modelMapperUtil;

    @Autowired
    public AdminControllerTest(ObjectMapper objectMapper,
                               MockMvc mockMvc,
                               PeopleService peopleService,
                               JWTUtil jwtUtil,
                               ModelMapperUtil modelMapperUtil) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
        this.peopleService = peopleService;
        this.jwtUtil = jwtUtil;
        this.modelMapperUtil = modelMapperUtil;
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
        getSearchPeopleThenCheckContains(peopleService.findAll().get(0));
    }

    @Test
    void setNewPersonData() throws Exception {
        Person person = peopleService.save(Utils.personUser);
        putNewPersonDataThenCheckIt(person);
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

    private void getSearchPeopleThenCheckContains(Person person) throws Exception {
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

    private void putNewPersonDataThenCheckIt(Person person) throws Exception {
        PersonDataDTO personDataDTO = modelMapperUtil.convertToPersonDataDTO(person);
        personDataDTO.setName("newName");
        Utils.putResultActionsWithTokenAndBody(mockMvc,
                        "/admin/set_new_person_data",
                        adminToken,
                        objectMapper,
                        personDataDTO)
                .andExpect(status().is(202));
        Person newPerson = peopleService.findById(person.getId());
        assertEquals(newPerson.getName(), personDataDTO.getName());

        personDataDTO.setName(person.getName());
        Utils.putResultActionsWithTokenAndBody(mockMvc,
                        "/admin/set_new_person_data",
                        adminToken,
                        objectMapper,
                        personDataDTO)
                .andExpect(status().is(202));
        newPerson = peopleService.findById(person.getId());
        assertEquals(newPerson.getName(), personDataDTO.getName());
    }
}
