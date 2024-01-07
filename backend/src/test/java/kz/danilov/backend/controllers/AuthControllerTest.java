package kz.danilov.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.danilov.backend.Utils;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

    @Autowired
    public AuthControllerTest(ObjectMapper objectMapper,
                              MockMvc mockMvc,
                              PeopleService peopleService,
                              TrainersService trainerService,
                              JWTUtil jwtUtil) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
        this.peopleService = peopleService;
        this.trainerService = trainerService;
        this.jwtUtil = jwtUtil;
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
        resetDb();
        personAdmin = peopleService.save(Utils.personAdmin);
        adminToken = jwtUtil.generateToken(personAdmin.getName());
        personTrainer = peopleService.save(Utils.personTrainer);
        trainerToken = jwtUtil.generateToken(personTrainer.getName());
        trainerService.saveNewTrainer(personTrainer.getId());
        personUser = peopleService.save(Utils.personUser);
        userToken = jwtUtil.generateToken(personUser.getName());
        personPremium = peopleService.save(Utils.personPremium);
        premiumToken = jwtUtil.generateToken(personPremium.getName());
    }

    @AfterAll
    public void after() {
        resetDb();
    }

    private void resetDb() {
        trainerService.deleteAll();
        peopleService.deleteAll();
    }

    @Test
    void performRegistrationUser() throws Exception {
        postRegistrationUserThenGetJWTTokenAndValidateIt();
        postRegistrationUserThenGetExceptionAboutUnique();
    }

    @Test
    void performRegistrationTrainer() throws Exception {
        postRegistrationTrainerThenGetJWTTokenAndValidateIt();
        postRegistrationTrainerThenGetExceptionAboutUnique();
    }

    @Test
    void performLogin() throws Exception {
        postLoginThenGetJWTTokenAndValidateIt();
        postLoginThenGetIncorrectCredentials();
    }

    @Test
    void check() throws Exception {
        getCheckThenGetBooleanTrue();
        getCheckThenGetBooleanFalse();
    }

    @Test
    void checkToken() throws Exception {
        getCheckTokenThenGetIt(userToken);
        //getCheckTokenThenGetIt(Utils.getOldToken()); TODO нужно доделать логику проверки токенов
    }

    @Test
    void getPersonData() throws Exception {
        getPersonDataThenCheckIt();
    }

    private void postRegistrationUserThenGetJWTTokenAndValidateIt() throws Exception {
        String userToken = Utils
                .postResultActionsWithoutTokenButWithBody(mockMvc,
                        "/auth/registration/user",
                        objectMapper,
                        Utils.createTestUserPersonDTO())
                .andExpect(jsonPath("jwt").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .replace("{\"jwt\":\"", "")
                .replace("\"}", "");

        assertTrue(jwtUtil.checkToken(userToken));
    }

    private void postRegistrationUserThenGetExceptionAboutUnique() throws Exception {
        String result = Utils
                .postResultActionsWithoutTokenButWithBody(mockMvc,
                        "/auth/registration/user",
                        objectMapper,
                        Utils.createTestUserPersonDTO())
                .andExpect(jsonPath("message").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, "{\"message\":\"error\"}");
    }

    private void postRegistrationTrainerThenGetJWTTokenAndValidateIt() throws Exception {
        PersonDTO personDTO = Utils.createTestTrainerPersonDTO();

        String  trainerToken = Utils
                .postResultActionsWithoutTokenButWithBody(mockMvc,
                        "/auth/registration/trainer",
                        objectMapper,
                        personDTO)
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

    private void postRegistrationTrainerThenGetExceptionAboutUnique() throws Exception {
        String  result = Utils
                .postResultActionsWithoutTokenButWithBody(mockMvc,
                        "/auth/registration/trainer",
                        objectMapper,
                        Utils.createTestTrainerPersonDTO())
                .andExpect(jsonPath("message").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, "{\"message\":\"error\"}");
    }

    private void postLoginThenGetJWTTokenAndValidateIt() throws Exception {
        String userToken = Utils
                .postResultActionsWithoutTokenButWithBody(mockMvc,
                        "/auth/login",
                        objectMapper,
                        new PersonDTO("user", "user"))
                .andExpect(jsonPath("jwt").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .replace("{\"jwt\":\"", "")
                .replace("\"}", "");

        assertTrue(jwtUtil.checkToken(userToken));
    }

    private void postLoginThenGetIncorrectCredentials() throws Exception {
        String result = Utils
                .postResultActionsWithoutTokenButWithBody(mockMvc,
                        "/auth/login",
                        objectMapper,
                        new PersonDTO("user", "trainer"))
                .andExpect(jsonPath("message").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, "{\"message\":\"Incorrect credentials!\"}");
    }

    private void getCheckThenGetBooleanTrue() throws Exception {
        String result = Utils
                .getResultActionsWithTokenButWithoutBody(mockMvc,
                        "/auth/check",
                        userToken)
                .andExpect(jsonPath("is_jwt_actual").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .replace("{\"is_jwt_actual\":", "")
                .replace("}", "");

        assertTrue(result.equalsIgnoreCase("true"));
    }

    private void getCheckThenGetBooleanFalse() throws Exception {
        String result = Utils
                .getResultActionsWithTokenButWithoutBody(mockMvc,
                        "/auth/check",
                        Utils.getOldToken())
                .andExpect(jsonPath("is_jwt_actual").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .replace("{\"is_jwt_actual\":", "")
                .replace("}", "");

        assertTrue(result.equalsIgnoreCase("false"));
    }


    private void getCheckTokenThenGetIt(String token) throws Exception {
        String newToken = Utils
                .getResultActionsWithTokenButWithoutBody(mockMvc,
                        "/auth/check_token",
                        token)
                .andExpect(jsonPath("jwt").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .replace("{\"jwt\":\"", "")
                .replace("\"}", "");

        assertEquals(newToken, token);
    }

    private void getPersonDataThenCheckIt() throws Exception {
        String data = Utils
                .getResultActionsWithTokenButWithoutBody(mockMvc,
                        "/auth/get_person_data",
                        userToken)
                .andExpect(jsonPath("person_data").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(data.contains(personUser.getName()));
    }
}
