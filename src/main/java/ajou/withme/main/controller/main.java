package ajou.withme.main.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class main {
    @GetMapping
    public String mainpage() {
        return "index page";
    }
}
