package kz.danilov.backend.services;

import kz.danilov.backend.util.exceptions.PersonNotFoundException;
import kz.danilov.backend.models.Person;
import kz.danilov.backend.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAllByOrderById();
    }

    public List<Person> findByPartNameAndSortById(String partName) {
        return peopleRepository.findByNameContainingIgnoreCaseOrderById(partName);
    }

    public Person findById(int id) {
        return peopleRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    @Transactional
    public Person save(Person person) {
        return peopleRepository.save(person);
    }

    @Transactional
    public void delete(Person person) {
        peopleRepository.delete(person);
    }

    @Transactional
    public void deleteAll() {
        peopleRepository.deleteAll();
    }
}
