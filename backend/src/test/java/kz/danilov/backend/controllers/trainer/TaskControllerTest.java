package kz.danilov.backend.controllers.trainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.danilov.backend.utils.Utils;
import kz.danilov.backend.models.Person;
import kz.danilov.backend.models.trainers.Exercise;
import kz.danilov.backend.models.trainers.Task;
import kz.danilov.backend.models.trainers.Trainer;
import kz.danilov.backend.security.JWTUtil;
import kz.danilov.backend.services.PeopleService;
import kz.danilov.backend.services.trainers.ExercisesService;
import kz.danilov.backend.services.trainers.TasksService;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User: Nikolai Danilov
 * Date: 07.01.2024
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskControllerTest {
    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;
    private final PeopleService peopleService;
    private final JWTUtil jwtUtil;
    private final ModelMapperUtil modelMapperUtil;
    private final TrainersService trainersService;
    private final TasksService tasksService;
    private final ExercisesService exercisesService;

    @Autowired
    public TaskControllerTest(ObjectMapper objectMapper, MockMvc mockMvc, PeopleService peopleService, JWTUtil jwtUtil, ModelMapperUtil modelMapperUtil, TrainersService trainersService, TasksService tasksService, ExercisesService exercisesService) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
        this.peopleService = peopleService;
        this.jwtUtil = jwtUtil;
        this.modelMapperUtil = modelMapperUtil;
        this.trainersService = trainersService;
        this.tasksService = tasksService;
        this.exercisesService = exercisesService;
    }

    private Person personTrainer = null;
    private Trainer trainer = null;
    private String trainerToken = null;
    private List<Task> tasks;
    private List<Exercise> exercises;

    @BeforeAll
    public void initialize() {
        resetDb();
        personTrainer = peopleService.save(Utils.personTrainer);
        trainer = trainersService.saveNewTrainer(personTrainer.getId());
        trainerToken = jwtUtil.generateToken(personTrainer.getName());

        exercises = Utils.getExercises(trainer);
        exercises.forEach(exercisesService::save);

        tasks = Utils.getTasks(trainer);
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            switch (i) {
                case 0, 1, 2, 13 -> task.setExercise(exercises.get(0));
                case 3, 4, 5, 14 -> task.setExercise(exercises.get(1));
                case 6, 7, 8, 9, 10, 11, 12 -> task.setExercise(exercises.get(2));
            }

            tasksService.save(task);
        }
    }

    @AfterAll
    public void after() {
        resetDb();
    }

    private void resetDb() {
        trainersService.deleteAll();
        peopleService.deleteAll();
        tasksService.deleteAll();
        exercisesService.deleteAll();
    }

    @Test
    void getAllTasks() throws Exception {
        getListTasksThenCheckIt();
    }

    private void getListTasksThenCheckIt() throws Exception {
        String data = Utils.getResultActionsWithTokenButWithoutBody(mockMvc,
                        "/trainer/task/all",
                        trainerToken)
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(data.contains("" + tasks.get(0).getId()));
    }
}
