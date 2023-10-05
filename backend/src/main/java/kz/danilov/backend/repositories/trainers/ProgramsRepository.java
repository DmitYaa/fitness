package kz.danilov.backend.repositories.trainers;

import kz.danilov.backend.models.trainers.Program;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: Nikolai Danilov
 * Date: 01.08.2023
 */
public interface ProgramsRepository extends JpaRepository<Program, Integer> {
}
