package kz.danilov.backend.services.trainers;

import kz.danilov.backend.models.trainers.Exercise;
import kz.danilov.backend.models.trainers.Trainer;
import kz.danilov.backend.repositories.trainers.TrainersRepository;
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
public class TrainersService {

    private final TrainersRepository trainersRepository;

    @Autowired
    public TrainersService(TrainersRepository trainersRepository) {
        this.trainersRepository = trainersRepository;
    }

    public Trainer findByPersonId(int personId) {
        return trainersRepository.findFirstByPersonId(personId).orElse(null);
    }
}
