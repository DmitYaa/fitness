package kz.danilov.backend.controllers.trainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.danilov.backend.Utils;
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

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * User: Nikolai Danilov
 * Date: 07.01.2024
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TrainerControllerTest {
    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;
    private final PeopleService peopleService;
    private final JWTUtil jwtUtil;
    private final ModelMapperUtil modelMapperUtil;
    private final TrainersService trainersService;

    @Autowired
    public TrainerControllerTest(ObjectMapper objectMapper, MockMvc mockMvc, PeopleService peopleService, JWTUtil jwtUtil, ModelMapperUtil modelMapperUtil, TrainersService trainersService) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
        this.peopleService = peopleService;
        this.jwtUtil = jwtUtil;
        this.modelMapperUtil = modelMapperUtil;
        this.trainersService = trainersService;
    }

    private Person personTrainer = null;
    private Trainer trainer = null;
    private String trainerToken = null;

    @BeforeAll
    public void initialize() {
        resetDb();
        personTrainer = peopleService.save(Utils.personTrainer);
        trainer = trainersService.saveNewTrainer(personTrainer.getId());
        trainerToken = jwtUtil.generateToken(personTrainer.getName());
    }

    @AfterAll
    public void after() {
        resetDb();
    }

    private void resetDb() {
        trainersService.deleteAll();
        peopleService.deleteAll();
    }

    @Test
    void getTrainer() throws Exception {
        getTrainerThenCheckIt();
    }

    private void getTrainerThenCheckIt() throws Exception {
        String data = Utils.getResultActionsWithTokenButWithoutBody(mockMvc,
                        "/trainer",
                        trainerToken)
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(data.contains("" + trainer.getRating()));
    }
}
