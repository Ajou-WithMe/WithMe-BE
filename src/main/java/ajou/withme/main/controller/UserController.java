package ajou.withme.main.controller;

import ajou.withme.main.Service.UserService;
import ajou.withme.main.domain.User;
import ajou.withme.main.dto.SignUpWithEmailDto;
import ajou.withme.main.util.ResFormat;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup/email")
    public ResFormat signUpWithEmail(@RequestBody SignUpWithEmailDto signUpWithEmailDto) {
        System.out.println("signUpWithEmailDto = " + signUpWithEmailDto);
        return null;
    }

    @PostMapping("signup/duplicate")
    public ResFormat isNotDuplicateEmail(@RequestParam String email) {
        User userByEmail = userService.findUserByEmail(email);
        Boolean check = userByEmail == null;
        return new ResFormat(true, 200L, check);
    }
}
