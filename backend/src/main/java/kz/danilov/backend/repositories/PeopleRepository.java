package kz.danilov.backend.repositories;

import kz.danilov.backend.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByName(String username);
}
