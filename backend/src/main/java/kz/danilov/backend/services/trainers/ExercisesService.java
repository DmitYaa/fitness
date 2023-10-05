package kz.danilov.backend.services.trainers;

import kz.danilov.backend.repositories.trainers.ExercisesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
