package kz.danilov.backend.controllers.trainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.danilov.backend.utils.Utils;
import kz.danilov.backend.dto.trainers.ExerciseDTO;
import kz.danilov.backend.dto.trainers.NewExerciseDTO;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.List;

import static kz.danilov.backend.utils.Utils.loadBuffer;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User: Nikolai Danilov
 * Date: 08.01.2024
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExerciseControllerTest {

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;
    private final PeopleService peopleService;
    private final JWTUtil jwtUtil;
    private final ModelMapperUtil modelMapperUtil;
    private final TrainersService trainersService;
    private final TasksService tasksService;
    private final ExercisesService exercisesService;

    @Autowired
    public ExerciseControllerTest(ObjectMapper objectMapper, MockMvc mockMvc, PeopleService peopleService, JWTUtil jwtUtil, ModelMapperUtil modelMapperUtil, TrainersService trainersService, TasksService tasksService, ExercisesService exercisesService) {
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
    void getAllExercises() throws Exception {
        getListExercisesThenCheckIt();
    }

    @Test
    void getImage() throws Exception {
        getImageThenCheckIt();
        getNonexistentImageThenCheckStatus();
    }

    @Test
    void getFullVideo() throws Exception {
        getVideoThenCheckIt();
        getNonexistentVideoThenCheckStatus();
    }

    @Test
    void postNewExercise() throws Exception {
        postNewExerciseThenCheckIt();
    }

    @Test
    void putEditeExercise() throws Exception {
        putEditeExerciseThenCheckIt();
    }

    @Test
    void putImage() throws Exception {
        String path = "D:\\Project\\fitness\\backend\\src\\test\\resources\\files\\press.jpg";
        byte[] buffer = loadBuffer(path);
        MockMultipartFile image = new MockMultipartFile("image", "press.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, buffer);
        putImageThenCheckIt(image);
    }

    @Test
    void putVideo() throws Exception {
        String path = "D:\\Project\\fitness\\backend\\src\\test\\resources\\files\\pressVideo.MOV";
        byte[] buffer = loadBuffer(path);
        MockMultipartFile video = new MockMultipartFile("video", "pressVideo.MOV", MediaType.MULTIPART_FORM_DATA_VALUE, buffer);

        putVideoThenCheckIt(video);
    }

    private void getListExercisesThenCheckIt() throws Exception {
        String data = Utils.getResultActionsWithTokenButWithoutBody(mockMvc,
                        "/trainer/exercise/all",
                        trainerToken)
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(data.contains("" + exercises.get(0).getId()));
    }

    private void getImageThenCheckIt() throws Exception {
        Utils.getResultActionsWithTokenButWithoutBody(mockMvc,
                        "/trainer/exercise/image/" + exercises.get(0).getId(),
                        trainerToken)
                .andExpect(status().is(200));
    }

    private void getNonexistentImageThenCheckStatus() throws Exception {
        Utils.getResultActionsWithTokenButWithoutBody(mockMvc,
                        "/trainer/exercise/image/" + -1,
                        trainerToken)
                .andExpect(status().is(404));
    }

    private void getVideoThenCheckIt() throws Exception {
        Utils.getResultActionsWithTokenButWithoutBody(mockMvc,
                        "/trainer/exercise/video/" + exercises.get(0).getId(),
                        trainerToken)
                .andExpect(status().is(206));
    }

    private void getNonexistentVideoThenCheckStatus() throws Exception {
        Utils.getResultActionsWithTokenButWithoutBody(mockMvc,
                        "/trainer/exercise/video/" + -1,
                        trainerToken)
                .andExpect(status().is(404));
    }

    private void postNewExerciseThenCheckIt() throws Exception {
        NewExerciseDTO newExerciseDTO = Utils.createTestNewExerciseDTO();
        String data = Utils.postResultActionsWithTokenAndBody(mockMvc,
                        "/trainer/exercise/new",
                        trainerToken,
                        objectMapper,
                        newExerciseDTO)
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(data.contains("id"));
    }

    private void putEditeExerciseThenCheckIt() throws Exception {
        ExerciseDTO exerciseDTO = Utils.createTestEditExerciseDTO(exercisesService.findAll().get(1), modelMapperUtil);
        Utils.putResultActionsWithTokenAndBody(mockMvc,
                        "/trainer/exercise/edite",
                        trainerToken,
                        objectMapper,
                        exerciseDTO)
                .andExpect(status().is(204));
    }

    private void putImageThenCheckIt(MockMultipartFile image) throws Exception {
        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/trainer/exercise/image/" + exercisesService.findAll().get(0).getId());
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });
        mockMvc.perform(builder
                        .file(image)
                        .header("Authorization", "Bearer " + trainerToken))
                .andExpect(status().is(204));
    }

    private void putVideoThenCheckIt(MockMultipartFile video) throws Exception {
        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/trainer/exercise/video/" + exercisesService.findAll().get(0).getId());
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });
        mockMvc.perform(builder
                        .file(video)
                        .header("Authorization", "Bearer " + trainerToken))
                .andExpect(status().is(204));
    }
}
