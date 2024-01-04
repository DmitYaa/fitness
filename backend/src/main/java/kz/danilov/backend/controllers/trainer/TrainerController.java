package kz.danilov.backend.controllers.trainer;

import kz.danilov.backend.BackendApplication;
import kz.danilov.backend.models.Person;
import kz.danilov.backend.models.trainers.Trainer;
import kz.danilov.backend.security.SecurityUtil;
import kz.danilov.backend.services.trainers.ExercisesService;
import kz.danilov.backend.services.trainers.TasksService;
import kz.danilov.backend.services.trainers.TrainersService;
import kz.danilov.backend.util.ModelMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/trainer")
public class TrainerController {

    private static final Logger log = LoggerFactory.getLogger(BackendApplication.class);

    private final TrainersService trainersService;
    private final ModelMapperUtil modelMapperUtil;

    @Autowired
    public TrainerController(TrainersService trainersService, ModelMapperUtil modelMapperUtil) {
        this.trainersService = trainersService;
        this.modelMapperUtil = modelMapperUtil;
    }

    @GetMapping
    public ResponseEntity<Trainer> getTrainer() {
        Person person = SecurityUtil.getPerson();
        log.info("GET: /trainer/exercises  personId = " + person.getId());

        Trainer trainer = trainersService.findByPersonId(person.getId());

        return ResponseEntity.status(HttpStatus.OK).body(trainer);
    }


}
