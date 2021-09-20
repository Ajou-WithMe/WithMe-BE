package ajou.withme.main.controller.user;

import ajou.withme.main.Service.MailService;
import ajou.withme.main.Service.UserService;
import ajou.withme.main.domain.User;
import ajou.withme.main.dto.user.SignUpWithEmailDto;
import ajou.withme.main.dto.user.SignUpWithKakaoDto;
import ajou.withme.main.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SignUpController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @PostMapping("/signup/email")
    public ResFormat signUpWithEmail(@RequestBody SignUpWithEmailDto signUpWithEmailDto) {

        String encodedPwd = passwordEncoder.encode(signUpWithEmailDto.getPwd());
        User user = signUpWithEmailDto.toEntity(encodedPwd);

        User savedUser = userService.saveUser(user);

        return new ResFormat(true, 201L, savedUser);
    }

    @PostMapping("/signup/kakao")
    public ResFormat signUpWithKakao(@RequestBody SignUpWithKakaoDto signUpWithKakaoDto) {

        User user = signUpWithKakaoDto.toEntity();

        User savedUser = userService.saveUser(user);

        return new ResFormat(true, 201L, savedUser);
    }

    @PostMapping("/signup/certification")
    public ResFormat sendCertificationCode(@RequestParam String email) throws MessagingException {

        String code = mailService.sendCertificationCodeMail(email);

        return new ResFormat(true, 201L, code);
    }

    @PostMapping("/signup/duplicate")
    public ResFormat isNotDuplicateEmail(@RequestParam String email) {

        User userByEmail = userService.findUserByEmail(email);
        boolean check = userByEmail == null;

        return new ResFormat(true, 200L, check);
    }

    @PostMapping("/signup/existUser")
    public ResFormat isNotExistUser(@RequestParam String uid) {

        User userByEmail = userService.findUserByUid(uid);
        boolean check = userByEmail == null;

        return new ResFormat(true, 200L, check);
    }

}
