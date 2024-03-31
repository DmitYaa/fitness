package kz.danilov.backend.services.trainers;

import kz.danilov.backend.models.trainers.Program;
import kz.danilov.backend.models.trainers.Training;
import kz.danilov.backend.repositories.trainers.TrainingsRepository;
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
public class TrainingsService {

    private final TrainingsRepository trainingsRepository;

    @Autowired
    public TrainingsService(TrainingsRepository trainingsRepository) {
        this.trainingsRepository = trainingsRepository;
    }

    public Training getById(int id) {
        return trainingsRepository.getById(id);
    }

    public List<Training> findAll() {
        return trainingsRepository.findAll();
    }

    @Transactional
    public Training save(Training training) {
        return trainingsRepository.save(training);
    }

    @Transactional
    public void deleteAll() {
        trainingsRepository.deleteAll();
    }
}
