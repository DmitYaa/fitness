package kz.danilov.backend.services.trainers;

import kz.danilov.backend.models.trainers.Exercise;
import kz.danilov.backend.repositories.trainers.ExercisesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Nikolai Danilov
 * Date: 01.08.2023
 */
@Service
@Transactional(readOnly = true)
public class ExercisesService {

    private final ExercisesRepository exercisesRepository;

    @Autowired
    public ExercisesService(ExercisesRepository exercisesRepository) {
        this.exercisesRepository = exercisesRepository;
    }

    public List<Exercise> findAll() {
        return exercisesRepository.findAll();
    }

    public Exercise findById(int id) {
        return exercisesRepository.findById(id).orElse(null);
    }

    @Transactional
    public int saveNewExercise(Exercise exercise) {
        Exercise newExercise = exercisesRepository.save(exercise);
        return newExercise.getId();
    }

    @Transactional
    public Exercise saveExercise(Exercise exercise) {
        return exercisesRepository.save(exercise);
    }
}
