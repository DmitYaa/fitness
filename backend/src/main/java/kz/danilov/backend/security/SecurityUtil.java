package kz.danilov.backend.security;

import kz.danilov.backend.models.Person;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * User: Nikolai Danilov
 * Date: 26.10.2023
 */
public class SecurityUtil {
    public static Person getPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return personDetails.getPerson();
    }
}
