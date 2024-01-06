package kz.danilov.backend;

import kz.danilov.backend.dto.PersonDTO;
import kz.danilov.backend.models.Person;

/**
 * User: Nikolai Danilov
 * Date: 04.01.2024
 */
public class Utils {

    public static final Person personAdmin = new Person(
            0,
            "admin",
            "$2a$10$NzE3.ehopU.LN6eqFby/nOtDNHq/x7XPjmxPqDoRKY2DDEqRlJl56", //admin
            "ROLE_ADMIN");
    public static final Person personTrainer = new Person(
            0,
            "trainer",
            "$2a$10$ziRMsJUXmUgTLSPenkWY4udkty4NOGmWuw/dyqjhZF3ky0mAAZsDK", //trainer
            "ROLE_TRAINER");
    public static final Person personUser = new Person(
            0,
            "user",
            "$2a$10$DVx9f1djz1PI/SK3J4uhseGbaGu0XuDZIJD/p8Zwthix.DvyXFtvm", //user
            "ROLE_USER");
    public static final Person personPremium = new Person(
            0,
            "premium",
            "$2a$10$2eAE6E/J1HXSw1EiQAukYeyxRdnzU73sbzbV1QvK.gNMdYwi43g.S", //premium
            "ROLE_PREMIUM"
    );



    public static PersonDTO createTestUserPersonDTO() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName("Tom");
        personDTO.setPassword("password");

        return personDTO;
    }

    public static PersonDTO createTestTrainerPersonDTO() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName("Trainer");
        personDTO.setPassword("Trainer");

        return personDTO;
    }

    public static String getOldToken() {
        return "eyJ0eXAiOiJKV1QiLCJhbGciOSiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJpc3MiOiJmaXRuZXNzIiwiZXhwIjoxNjk2MTY0MDg5LCJpYXQiOjE2OTYxNjIyODksInVzZXJuYW1lIjoidGVzdCJ9.NRQcNhk7HqN71nqfR2Icv3f3nTbylXVBj2fixCY7608";
    }
}
