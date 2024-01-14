package kz.danilov.backend.controllers.trainer;

import kz.danilov.backend.BackendApplication;
import kz.danilov.backend.dto.trainers.NewTaskDTO;
import kz.danilov.backend.dto.trainers.TaskDTO;
import kz.danilov.backend.models.Person;
import kz.danilov.backend.models.trainers.Exercise;
import kz.danilov.backend.models.trainers.Task;
import kz.danilov.backend.models.trainers.Trainer;
import kz.danilov.backend.security.SecurityUtil;
import kz.danilov.backend.services.trainers.ExercisesService;
import kz.danilov.backend.services.trainers.TasksService;
import kz.danilov.backend.services.trainers.TrainersService;
import kz.danilov.backend.util.ModelMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User: Nikolai Danilov
 * Date: 04.01.2024
 */
@RestController
@RequestMapping("/trainer/task")
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(BackendApplication.class);

    private final TrainersService trainersService;
    private final ExercisesService exercisesService;
    private final TasksService tasksService;
    private final ModelMapperUtil modelMapperUtil;

    @Autowired
    public TaskController(TrainersService trainersService, ExercisesService exercisesService, TasksService tasksService, ModelMapperUtil modelMapperUtil) {
        this.trainersService = trainersService;
        this.exercisesService = exercisesService;
        this.tasksService = tasksService;
        this.modelMapperUtil = modelMapperUtil;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        Person person = SecurityUtil.getPerson();
        log.info("GET: /trainer/tasks  personId = " + person.getId());

        Trainer trainer = trainersService.findByPersonId(person.getId());
        List<Task> tasks = trainer.getTasks();

        return ResponseEntity.status(HttpStatus.OK).body(modelMapperUtil.convertToListOfTaskDTO(tasks));
    }

    @PostMapping("/new")
    public ResponseEntity<TaskDTO> postNewTask(@RequestBody NewTaskDTO newTaskDTO) {
        Person person = SecurityUtil.getPerson();
        log.info("POST: /trainer/tasks/new" + "  personId = " + person.getId());

        Trainer trainer = trainersService.findByPersonId(person.getId());
        Exercise exercise = exercisesService.findById(newTaskDTO.getIdExercise());
        TaskDTO taskDTO = modelMapperUtil.convertToTaskDTO(newTaskDTO);
        taskDTO.setExercise(exercise);
        Task task = modelMapperUtil.convertToTask(taskDTO);
        task.setTrainer(trainer);

        Task newTask = tasksService.save(task);

        return ResponseEntity.status(HttpStatus.OK).body(modelMapperUtil.convertToTaskDTO(newTask));
    }

    @PutMapping("/edite")
    public ResponseEntity<?> putEditeTask(@RequestBody TaskDTO taskDTO) {
        Person person = SecurityUtil.getPerson();
        log.info("PUT: /trainer/tasks/edite" + "  personId = " + person.getId());

        Task task = modelMapperUtil.convertToTask(taskDTO);
        Trainer trainer = trainersService.findByPersonId(person.getId());
        task.setTrainer(trainer);
        tasksService.save(task);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
