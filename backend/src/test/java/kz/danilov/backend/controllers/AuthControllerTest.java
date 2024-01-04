package kz.danilov.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.danilov.backend.Util;
import kz.danilov.backend.dto.PersonDTO;
import kz.danilov.backend.models.Person;
import kz.danilov.backend.models.trainers.Trainer;
import kz.danilov.backend.security.JWTUtil;
import kz.danilov.backend.services.PeopleService;
import kz.danilov.backend.services.trainers.TrainersService;
import kz.danilov.backend.util.ModelMapperUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * User: Nikolai Danilov
 * Date: 04.01.2024
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthControllerTest {
    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;
    private final PeopleService peopleService;
    private final TrainersService trainerService;
    private final JWTUtil jwtUtil;
    private final ModelMapperUtil modelMapperUtil;

    @Autowired
    public AuthControllerTest(ObjectMapper objectMapper,
                              MockMvc mockMvc,
                              PeopleService peopleService,
                              TrainersService trainerService,
                              JWTUtil jwtUtil,
                              ModelMapperUtil modelMapperUtil) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
        this.peopleService = peopleService;
        this.trainerService = trainerService;
        this.jwtUtil = jwtUtil;
        this.modelMapperUtil = modelMapperUtil;
    }

    private Person personAdmin = null;
    private String adminToken = null;
    private Person personTrainer = null;
    private String trainerToken = null;
    private Person personUser = null;
    private String userToken = null;
    private Person personPremium = null;
    private String premiumToken = null;

    @BeforeAll
    public void initialize() {
        personAdmin = peopleService.save(new Person(
                0,
                "admin",
                "$2a$10$NzE3.ehopU.LN6eqFby/nOtDNHq/x7XPjmxPqDoRKY2DDEqRlJl56", //admin
                "ROLE_ADMIN"));
        adminToken = jwtUtil.generateToken(personAdmin.getName());
        personTrainer = peopleService.save(new Person(
                0,
                "trainer",
                "$2a$10$ziRMsJUXmUgTLSPenkWY4udkty4NOGmWuw/dyqjhZF3ky0mAAZsDK", //trainer
                "ROLE_TRAINER"));
        trainerToken = jwtUtil.generateToken(personTrainer.getName());
        trainerService.saveNewTrainer(personTrainer.getId());
        personUser = peopleService.save(new Person(
                0,
                "user",
                "$2a$10$DVx9f1djz1PI/SK3J4uhseGbaGu0XuDZIJD/p8Zwthix.DvyXFtvm", //user
                "ROLE_USER"));
        userToken = jwtUtil.generateToken(personUser.getName());
        personPremium = peopleService.save(new Person(
                0,
                "premium",
                "$2a$10$2eAE6E/J1HXSw1EiQAukYeyxRdnzU73sbzbV1QvK.gNMdYwi43g.S", //premium
                "ROLE_PREMIUM"));
        premiumToken = jwtUtil.generateToken(personPremium.getName());
    }

    @Test
    public void postRegistrationUserThenGetJWTTokenAndValidateIt() throws Exception {
        String userToken = mockMvc.perform(post("/auth/registration/user")
                        .content(objectMapper.writeValueAsString(Util.createTestUserPersonDTO()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("jwt").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .replace("{\"jwt\":\"", "")
                .replace("\"}", "");

        assertTrue(jwtUtil.checkToken(userToken));
    }

    @Test
    public void postRegistrationTrainerThenGetJWTTokenAndValidateIt() throws Exception {
        PersonDTO personDTO = Util.createTestTrainerPersonDTO();

        String  trainerToken = mockMvc.perform(post("/auth/registration/trainer")
                        .content(objectMapper.writeValueAsString(personDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("jwt").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .replace("{\"jwt\":\"", "")
                .replace("\"}", "");

        assertTrue(jwtUtil.checkToken(trainerToken));

        boolean result = false;
        int personId = -1;
        List<Person> people = peopleService.findAll();
        for (Person person : people) {
            if (person.getName().equals(personDTO.getName())) {
                if (person.getRole().equals("ROLE_TRAINER")) {
                    personId = person.getId();
                    result = true;
                    break;
                }
            }
        }
        assertTrue(result);

        Trainer t = trainerService.findByPersonId(personId);
        assertNotNull(t);
    }

    @Test
    public void postLoginThenGetJWTTokenAndValidateIt() throws Exception {
        String userToken = mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(new PersonDTO("user", "user")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("jwt").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .replace("{\"jwt\":\"", "")
                .replace("\"}", "");

        assertTrue(jwtUtil.checkToken(userToken));
    }

    @Test
    public void getCheckThenGetBooleanTrue() throws Exception {
        String result = mockMvc.perform(get("/auth/check")
                        .header("Authorization", "Bearer " + userToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("is_jwt_actual").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .replace("{\"is_jwt_actual\":", "")
                .replace("}", "");

        assertTrue(result.equalsIgnoreCase("true"));
    }

    @AfterAll
    public void resetDb() {
        trainerService.deleteAll();
        peopleService.deleteAll();
    }
}
