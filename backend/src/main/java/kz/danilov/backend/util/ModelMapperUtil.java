package kz.danilov.backend.util;

import kz.danilov.backend.dto.PersonDTO;
import kz.danilov.backend.dto.PersonDataDTO;
import kz.danilov.backend.dto.PersonOnlyWithNameDTO;
import kz.danilov.backend.models.Person;
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

    public Person convertToPerson(PersonOnlyWithNameDTO personOnlyWithNameDTO) {
        return this.modelMapper.map(personOnlyWithNameDTO, Person.class);
    }
}
