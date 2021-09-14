package ajou.withme.main.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
public class RootController {
    @GetMapping
    public String mainpage(HttpSession httpSession)
    {
        log.error(httpSession.getId());
        return "Getting Index Success";
    }
}
