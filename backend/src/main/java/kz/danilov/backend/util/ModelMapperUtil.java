package kz.danilov.backend.util;

import kz.danilov.backend.dto.PersonDTO;
import kz.danilov.backend.dto.PersonDataDTO;
import kz.danilov.backend.dto.PersonOnlyWithNameDTO;
import kz.danilov.backend.dto.trainers.ExerciseDTO;
import kz.danilov.backend.dto.trainers.NewExerciseDTO;
import kz.danilov.backend.dto.trainers.TaskDTO;
import kz.danilov.backend.models.Person;
import kz.danilov.backend.models.trainers.Exercise;
import kz.danilov.backend.models.trainers.Task;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModelMapperUtil {
    private final ModelMapper modelMapper;

    @Autowired
    public ModelMapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PersonDTO convertToPersonDTO(Person person) {
        return this.modelMapper.map(person, PersonDTO.class);
    }

    public Person convertToPerson(PersonDTO personDTO) {
        return this.modelMapper.map(personDTO, Person.class);
    }

    public PersonDataDTO convertToPersonDataDTO(Person person) {
        return this.modelMapper.map(person, PersonDataDTO.class);
    }

    public List<PersonDataDTO> convertToPersonDataDTOList(List<Person> persons) {
        List<PersonDataDTO> personDataDTOList = new ArrayList<>(persons.size());
        for (Person person : persons) {
            personDataDTOList.add(convertToPersonDataDTO(person));
        }

        return personDataDTOList;
    }

    public List<ExerciseDTO> convertToListOfExerciseDTO(List<Exercise> exercises) {
        List<ExerciseDTO> exerciseDTOList = new ArrayList<>(exercises.size());
        for (Exercise exercise : exercises) {
            exerciseDTOList.add(this.modelMapper.map(exercise, ExerciseDTO.class));
        }

        return exerciseDTOList;
    }

    public Exercise convertToExercise(NewExerciseDTO newExerciseDTO) {
        return this.modelMapper.map(newExerciseDTO, Exercise.class);
    }

    public ExerciseDTO convertToExerciseDTO(Exercise exercise) {
        return this.modelMapper.map(exercise, ExerciseDTO.class);
    }

    public List<TaskDTO> convertToListOfTaskDTO(List<Task> tasks) {
        List<TaskDTO> tasksDTOList = new ArrayList<>(tasks.size());
        for (Task task : tasks) {
            tasksDTOList.add(this.modelMapper.map(task, TaskDTO.class));
        }

        return tasksDTOList;
    }
}
