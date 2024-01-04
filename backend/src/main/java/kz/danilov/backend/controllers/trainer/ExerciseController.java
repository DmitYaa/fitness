package kz.danilov.backend.controllers.trainer;

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
import org.springframework.beans.factory.annotation.Value;
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

/**
 * User: Nikolai Danilov
 * Date: 04.01.2024
 */
@Controller
@RequestMapping("/trainer/exercise")
public class ExerciseController {
    private static final Logger log = LoggerFactory.getLogger(BackendApplication.class);

    @Value("${path_to_files}")
    private String pathToFiles;
    private final TrainersService trainersService;
    private final ExercisesService exercisesService;
    private final ModelMapperUtil modelMapperUtil;

    @Autowired
    public ExerciseController(TrainersService trainersService, ExercisesService exercisesService, ModelMapperUtil modelMapperUtil) {
        this.trainersService = trainersService;
        this.exercisesService = exercisesService;
        this.modelMapperUtil = modelMapperUtil;
    }

    @GetMapping("/all")
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

    @PostMapping("/new")
    public ResponseEntity<Integer> postNewExercise(@RequestBody NewExerciseDTO newExerciseDTO) {
        Person person = SecurityUtil.getPerson();
        log.info("POST: /new_exercise" + "  personId = " + person.getId());

        Trainer trainer = trainersService.findByPersonId(person.getId());
        Exercise exercise = modelMapperUtil.convertToExercise(newExerciseDTO);
        exercise.setTrainer(trainer);
        exercise.setImage(pathToFiles + "\\simple_image.jpg");
        exercise.setVideo(pathToFiles + "\\simple_video.mp4");

        int id = exercisesService.saveNewExercise(exercise);

        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PutMapping("/edite")
    public ResponseEntity<?> postEditeExercise(@RequestBody ExerciseDTO exerciseDTO) {
        Person person = SecurityUtil.getPerson();
        log.info("POST: /new_exercise" + "  personId = " + person.getId());

        Exercise exercise = exercisesService.findById(exerciseDTO.getId());
        exercise.setName(exerciseDTO.getName());
        exercise.setMuscle(exerciseDTO.getMuscle());
        exercise.setDescription(exerciseDTO.getDescription());

        exercisesService.saveExercise(exercise);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/image/{id}")
    public ResponseEntity<?> putImage(@PathVariable("id") int id, @RequestParam("image") MultipartFile image) {
        Person person = SecurityUtil.getPerson();
        log.info("PUT: /image/" + id +  "  personId = " + person.getId());

        String fileName = image.getOriginalFilename();
        try {
            File file = new File(pathToFiles + "\\" + id + "_" + fileName);
            image.transferTo(file);

            Exercise exercise = exercisesService.findById(id);
            exercise.setImage(file.getPath());
            exercisesService.saveExercise(exercise);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/video/{id}")
    public ResponseEntity<?> putVideo(@PathVariable("id") int id, @RequestParam("video") MultipartFile video) {
        Person person = SecurityUtil.getPerson();
        log.info("PUT: /video/" + id +  "  personId = " + person.getId());

        String fileName = video.getOriginalFilename();
        try {
            File file = new File(pathToFiles + "\\" + id + "_" + fileName);
            video.transferTo(file);

            Exercise exercise = exercisesService.findById(id);
            exercise.setVideo(file.getPath());
            exercisesService.saveExercise(exercise);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
