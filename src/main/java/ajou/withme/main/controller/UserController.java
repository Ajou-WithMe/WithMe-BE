package ajou.withme.main.controller;

import ajou.withme.main.Service.UserService;
import ajou.withme.main.domain.User;
import ajou.withme.main.dto.LoginWithEmailDto;
import ajou.withme.main.dto.SignUpWithEmailDto;
import ajou.withme.main.util.ResFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup/email")
    public ResFormat signUpWithEmail(@RequestBody SignUpWithEmailDto signUpWithEmailDto) {

        String encodedPwd = passwordEncoder.encode(signUpWithEmailDto.getPwd());
        User user = signUpWithEmailDto.toEntity(encodedPwd);

        User savedUser = userService.saveUser(user);

        return new ResFormat(true, 201L, savedUser);
    }

    @PostMapping("signup/duplicate")
    public ResFormat isNotDuplicateEmail(@RequestParam String email) {

        User userByEmail = userService.findUserByEmail(email);
        boolean check = userByEmail == null;

        return new ResFormat(true, 200L, check);
    }

    @PostMapping("/login/email")
    public ResFormat loginWithEmail(@RequestBody LoginWithEmailDto loginWithEmailDto) {

        User userByEmail = userService.findUserByEmail(loginWithEmailDto.getEmail());
        boolean isLogin = passwordEncoder.matches(loginWithEmailDto.getPwd(), userByEmail.getPwd());

        if (isLogin) {
            // login 성공
            return new ResFormat(true, 201L, isLogin);
        } else {
            // login 실패
            return new ResFormat(false, 400L, isLogin);

        }
    }
}
