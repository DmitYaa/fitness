package kz.danilov.backend;

import kz.danilov.backend.controllers.*;
import kz.danilov.backend.controllers.trainer.*;
import kz.danilov.backend.services.*;
import kz.danilov.backend.services.trainers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class BackendApplicationTests {

    private final AuthController authController;
    private final AdminController adminController;
    private final MainController mainController;
    private final ExerciseController exerciseController;
    private final TaskController taskController;
    private final TrainerController trainerController;

    private final PeopleService peopleService;
    private final PersonDetailsService personDetailsService;
    private final RegistrationService registrationService;
    private final ExercisesService exercisesService;
    private final ProgramsService programsService;
    private final TasksService tasksService;
    private final TrainersService trainersService;
    private final TrainingsService trainingsService;

    @Autowired
    public BackendApplicationTests(AuthController authController, AdminController adminController, MainController mainController, ExerciseController exerciseController, TaskController taskController, TrainerController trainerController, PeopleService peopleService, PersonDetailsService personDetailsService, RegistrationService registrationService, ExercisesService exercisesService, ProgramsService programsService, TasksService tasksService, TrainersService trainersService, TrainingsService trainingsService) {
        this.authController = authController;
        this.adminController = adminController;
        this.mainController = mainController;
        this.exerciseController = exerciseController;
        this.taskController = taskController;
        this.trainerController = trainerController;
        this.peopleService = peopleService;
        this.personDetailsService = personDetailsService;
        this.registrationService = registrationService;
        this.exercisesService = exercisesService;
        this.programsService = programsService;
        this.tasksService = tasksService;
        this.trainersService = trainersService;
        this.trainingsService = trainingsService;
    }

    @Test
    void contextLoads() {
        assertThat(authController).isNotNull();
        assertThat(adminController).isNotNull();
        assertThat(mainController).isNotNull();
        assertThat(exerciseController).isNotNull();
        assertThat(taskController).isNotNull();
        assertThat(trainerController).isNotNull();

        assertThat(peopleService).isNotNull();
        assertThat(personDetailsService).isNotNull();
        assertThat(registrationService).isNotNull();
        assertThat(exercisesService).isNotNull();
        assertThat(programsService).isNotNull();
        assertThat(tasksService).isNotNull();
        assertThat(trainersService).isNotNull();
        assertThat(trainingsService).isNotNull();
    }
}
