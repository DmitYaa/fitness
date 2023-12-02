package kz.danilov.backend.controllers;

import kz.danilov.backend.BackendApplication;
import kz.danilov.backend.dto.AuthenticationDTO;
import kz.danilov.backend.dto.PersonDataDTO;
import kz.danilov.backend.dto.SearchDTO;
import kz.danilov.backend.models.Person;
import kz.danilov.backend.services.PeopleService;
import kz.danilov.backend.util.ErrorResponse;
import kz.danilov.backend.util.ModelMapperUtil;
import kz.danilov.backend.util.exceptions.PersonNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger log = LoggerFactory.getLogger(BackendApplication.class);

    private final PeopleService peopleService;
    private final ModelMapperUtil modelMapperUtil;

    @Autowired
    public AdminController(PeopleService peopleService, ModelMapperUtil modelMapperUtil) {
        this.peopleService = peopleService;
        this.modelMapperUtil = modelMapperUtil;
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable("id") int id) {
        log.info("GET: /admin/person/" + id);

        Person person = peopleService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(person);
    }

    @GetMapping("/people")
    public ResponseEntity<List<PersonDataDTO>> getAllPeople() {
        log.info("GET: /admin/people");

        List<Person> people = peopleService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(modelMapperUtil.convertToPersonDataDTOList(people));
    }

    @PostMapping("/search_people")
    public ResponseEntity<List<PersonDataDTO>> getSearchPeople(@RequestBody SearchDTO searchDTO) {
        log.info("GET: /admin/search_people");

        List<Person> people = peopleService.findByPartNameAndSortById(searchDTO.getSearchString());

        return ResponseEntity.status(HttpStatus.OK).body(modelMapperUtil.convertToPersonDataDTOList(people));
    }

    @GetMapping("/people_size")
    public ResponseEntity<Integer> getPeopleSize() {
        log.info("GET: /admin/people_size");

        List<Person> people = peopleService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(people.size());
    }

    @PutMapping("/set_new_person_data")
    public ResponseEntity<HttpStatus> setNewPersonData(@RequestBody PersonDataDTO personDataDTO) {
        log.info("PUT: /admin/set_new_data, id = " + personDataDTO.getId());

        Person person = peopleService.findById(personDataDTO.getId());
        person.setName(personDataDTO.getName());
        person.setRole(personDataDTO.getRole());

        peopleService.save(person);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(PersonNotFoundException e) {
        ErrorResponse response = new ErrorResponse("Person with this id wasn't found!");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
