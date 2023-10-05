package kz.danilov.backend.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import kz.danilov.backend.BackendApplication;
import kz.danilov.backend.dto.AuthenticationDTO;
import kz.danilov.backend.dto.JwtDTO;
import kz.danilov.backend.dto.PersonDTO;
import kz.danilov.backend.dto.PersonDataDTO;
import kz.danilov.backend.models.Person;
import kz.danilov.backend.security.JWTUtil;
import kz.danilov.backend.security.PersonDetails;
import kz.danilov.backend.services.RegistrationService;
import kz.danilov.backend.util.ErrorResponse;
import kz.danilov.backend.util.ModelMapperUtil;
import kz.danilov.backend.util.validators.PersonValidator;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Enumeration;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;
    private final ModelMapperUtil modelMapperUtil;
    private final AuthenticationManager authenticationManager;

    private static final Logger log = LoggerFactory.getLogger(BackendApplication.class);

    @Autowired
    public AuthController(RegistrationService registrationService, PersonValidator personValidator, JWTUtil jwtUtil, ModelMapperUtil modelMapperUtil, AuthenticationManager authenticationManager) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
        this.jwtUtil = jwtUtil;
        this.modelMapperUtil = modelMapperUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration/user")
    public ResponseEntity<Map<String, String>> performRegistrationUser(@RequestBody @Valid PersonDTO personDTO,
                                                                       BindingResult bindingResult) {
        Person person = modelMapperUtil.convertToPerson(personDTO);

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "error"));

        registrationService.registerUser(person);

        String token = jwtUtil.generateToken(person.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("jwt", token));
    }

    @PostMapping("/registration/trainer")
    public ResponseEntity<Map<String, String>> performRegistrationTrainer(@RequestBody @Valid PersonDTO personDTO,
                                                   BindingResult bindingResult) {
        Person person = modelMapperUtil.convertToPerson(personDTO);

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "error"));

        registrationService.registerTrainer(person);

        String token = jwtUtil.generateToken(person.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("jwt", token));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        authenticationDTO.getName(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Incorrect credentials!"));
        }

        String token = jwtUtil.generateToken(authenticationDTO.getName());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("jwt", token));
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, Boolean>> check(@RequestHeader("authorization") String authorization) {
        String token = authorization.substring(7);
        log.info("post: /check, jwt = " + token);

        //log.info("В случаеотпраавки старого токена происходит ошибка на фронте, старый токен для проверки = eyJ0eXAiOiJKV1QiLCJhbGciOSiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJpc3MiOiJmaXRuZXNzIiwiZXhwIjoxNjk2MTY0MDg5LCJpYXQiOjE2OTYxNjIyODksInVzZXJuYW1lIjoidGVzdCJ9.NRQcNhk7HqN71nqfR2Icv3f3nTbylXVBj2fixCY7608");

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("is_jwt_actual", jwtUtil.checkToken(token)));
    }

    @GetMapping("/check_token")
    public ResponseEntity<Map<String, String>> checkToken(@RequestHeader("authorization") String authorization) {
        String token = authorization.substring(7);
        log.info("post: /check_token, jwt = " + token);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("jwt", jwtUtil.validateTokenAndGetNewTokenIfNeeded(token)));
    }

    @GetMapping("/get_person_data")
    public ResponseEntity<Map<String, PersonDataDTO>> getPersonData(@RequestHeader("authorization") String authorization) {
        String token = authorization.substring(7);
        log.info("get: /get_person_data, token = " + token);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = ((PersonDetails) authentication.getPrincipal()).getPerson();

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("person_data", modelMapperUtil.convertToPersonDataDTO(person)));
    }

    /*@ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(JWTVerificationException e) {
        ErrorResponse response = new ErrorResponse("Ошибка верификации");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }*/
}
