package kz.danilov.backend.repositories;

import kz.danilov.backend.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PeopleRepository extends JpaRepository<Person, Integer> {

    List<Person> findAllByOrderById();

    List<Person> findByNameContainingIgnoreCaseOrderById(String name);

    Optional<Person> findByName(String username);
}
