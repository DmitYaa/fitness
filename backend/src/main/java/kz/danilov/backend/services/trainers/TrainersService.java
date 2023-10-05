package kz.danilov.backend.services.trainers;

import kz.danilov.backend.repositories.trainers.TrainersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
