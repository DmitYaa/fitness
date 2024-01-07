package kz.danilov.backend.controllers.trainer;

import kz.danilov.backend.BackendApplication;
import kz.danilov.backend.dto.trainers.TaskDTO;
import kz.danilov.backend.models.Person;
import kz.danilov.backend.models.trainers.Task;
import kz.danilov.backend.models.trainers.Trainer;
import kz.danilov.backend.security.SecurityUtil;
import kz.danilov.backend.services.trainers.TrainersService;
import kz.danilov.backend.util.ModelMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final ModelMapperUtil modelMapperUtil;

    @Autowired
    public TaskController(TrainersService trainersService, ModelMapperUtil modelMapperUtil) {
        this.trainersService = trainersService;
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
}
