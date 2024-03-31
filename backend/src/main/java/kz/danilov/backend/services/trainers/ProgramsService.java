package kz.danilov.backend.services.trainers;

import kz.danilov.backend.models.trainers.Program;
import kz.danilov.backend.models.trainers.Task;
import kz.danilov.backend.repositories.trainers.ProgramsRepository;
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
public class ProgramsService {

    private final ProgramsRepository programsRepository;

    @Autowired
    public ProgramsService(ProgramsRepository programsRepository) {
        this.programsRepository = programsRepository;
    }

    public Program getById(int id) {
        return programsRepository.getById(id);
    }

    public List<Program> findAll() {
        return programsRepository.findAll();
    }

    @Transactional
    public Program save(Program program) {
        return programsRepository.save(program);
    }

    @Transactional
    public void deleteAll() {
        programsRepository.deleteAll();
    }
}
