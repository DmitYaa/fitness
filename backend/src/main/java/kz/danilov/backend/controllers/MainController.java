package kz.danilov.backend.controllers;

import kz.danilov.backend.BackendApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(BackendApplication.class);

   /* @GetMapping
    public String getHomePage() {
        log.info("get: /");
        return "index";
    }*/
}
