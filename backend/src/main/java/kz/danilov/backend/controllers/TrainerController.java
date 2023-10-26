package kz.danilov.backend.controllers;

import kz.danilov.backend.BackendApplication;
import kz.danilov.backend.dto.PersonDataDTO;
import kz.danilov.backend.models.Person;
import kz.danilov.backend.models.trainers.Exercise;
import kz.danilov.backend.models.trainers.Trainer;
import kz.danilov.backend.security.PersonDetails;
import kz.danilov.backend.security.SecurityUtil;
import kz.danilov.backend.services.trainers.ExercisesService;
import kz.danilov.backend.services.trainers.TrainersService;
import kz.danilov.backend.util.ModelMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/trainer")
public class TrainerController {

    private static final Logger log = LoggerFactory.getLogger(BackendApplication.class);

    private final TrainersService trainersService;
    private final ExercisesService exercisesService;
    private final ModelMapperUtil modelMapperUtil;

    @Autowired
    public TrainerController(TrainersService trainersService, ExercisesService exercisesService, ModelMapperUtil modelMapperUtil) {
        this.trainersService = trainersService;
        this.exercisesService = exercisesService;
        this.modelMapperUtil = modelMapperUtil;
    }

    @GetMapping
    public ResponseEntity<Trainer> getTrainer() {
        Person person = SecurityUtil.getPerson();
        log.info("GET: /trainer/exercises  personId = " + person.getId());

        Trainer trainer = trainersService.findByPersonId(person.getId());

        return ResponseEntity.status(HttpStatus.OK).body(trainer);
    }

    @GetMapping("/exercises")
    public ResponseEntity<List<Exercise>> getAllExercises() {
        Person person = SecurityUtil.getPerson();
        log.info("GET: /trainer/exercises  personId = " + person.getId());

        Trainer trainer = trainersService.findByPersonId(person.getId());
        List<Exercise> exercises = trainer.getExercises();

        return ResponseEntity.status(HttpStatus.OK).body(exercises);
    }
}
