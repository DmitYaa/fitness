package kz.danilov.backend;

import kz.danilov.backend.dto.PersonDTO;

/**
 * User: Nikolai Danilov
 * Date: 04.01.2024
 */
public class Util {

    public static PersonDTO createTestLoginPersonDTO() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName("Tom");
        personDTO.setPassword("password");

        return personDTO;
    }
}
