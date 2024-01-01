package kz.danilov.backend.controllers;

import kz.danilov.backend.BackendApplication;
import kz.danilov.backend.dto.trainers.ExerciseDTO;
import kz.danilov.backend.dto.trainers.NewExerciseDTO;
import kz.danilov.backend.models.Person;
import kz.danilov.backend.models.trainers.Exercise;
import kz.danilov.backend.models.trainers.Trainer;
import kz.danilov.backend.security.SecurityUtil;
import kz.danilov.backend.services.trainers.ExercisesService;
import kz.danilov.backend.services.trainers.TrainersService;
import kz.danilov.backend.util.ModelMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
    public ResponseEntity<List<ExerciseDTO>> getAllExercises() {
        Person person = SecurityUtil.getPerson();
        log.info("GET: /trainer/exercises  personId = " + person.getId());

        Trainer trainer = trainersService.findByPersonId(person.getId());
        List<Exercise> exercises = trainer.getExercises();

        return ResponseEntity.status(HttpStatus.OK).body(modelMapperUtil.convertToListOfExerciseDTO(exercises));
    }

    @GetMapping("/get_image/{id}")
    public ResponseEntity<?> getImage(@PathVariable("id") int id) throws IOException {
        Person person = SecurityUtil.getPerson();
        log.info("GET: /trainer/get_image/" + id +   "  personId = " + person.getId());

        Exercise exercise = exercisesService.findById(id);
        Trainer trainer = trainersService.findByPersonId(person.getId());

        if (exercise != null && exercise.getTrainer() == trainer) {
            byte[] image = Files.readAllBytes(new File(exercise.getImage()).toPath());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/jpeg"))
                    .body(image);
        } else
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).body("exercise not fund");
    }

    @GetMapping("/get_video/{id}")
    public ResponseEntity<FileSystemResource> getFullVideo(@PathVariable("id") int id) {
        Person person = SecurityUtil.getPerson();
        log.info("GET: /trainer/get_video/" + id +   "  personId = " + person.getId());

        Exercise exercise = exercisesService.findById(id);
        Trainer trainer = trainersService.findByPersonId(person.getId());
        if (exercise != null && exercise.getTrainer() == trainer) {
            return ResponseEntity
                    .status(HttpStatus.PARTIAL_CONTENT)
                    .body(new FileSystemResource(exercise.getVideo()));
        } else
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("/new_exercise")
    public ResponseEntity<Integer> postNewExercise(@RequestBody NewExerciseDTO newExerciseDTO) {
        Person person = SecurityUtil.getPerson();
        log.info("GET: /new_exercise" + "  personId = " + person.getId());

        log.info("нужно записать в БД exerciseDTO = " + newExerciseDTO);
        log.info("и вернуть его id из базы");
        int id = 1;

        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PutMapping("/image/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putImage(@PathVariable("id") int id, @RequestParam("image") MultipartFile image) {
        Person person = SecurityUtil.getPerson();
        log.info("GET: /image/" + id +  "  personId = " + person.getId());

        String fileName = image.getOriginalFilename();
        log.info("Надо обработать файл с fileName = " + fileName);
    }
}
