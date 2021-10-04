package ajou.withme.main.controller.user;

import ajou.withme.main.Service.MailService;
import ajou.withme.main.Service.UserService;
import ajou.withme.main.domain.User;
import ajou.withme.main.dto.user.request.SignUpWithEmailDto;
import ajou.withme.main.dto.user.request.SignUpWithKakaoDto;
import ajou.withme.main.dto.user.request.UserEmailDto;
import ajou.withme.main.dto.user.request.UserUidDto;
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

        return new ResFormat(true, 201L, "회원가입을 완료했습니다.");
    }

    @PostMapping("/signup/kakao")
    public ResFormat signUpWithKakao(@RequestBody SignUpWithKakaoDto signUpWithKakaoDto) {

        User user = signUpWithKakaoDto.toEntity();

        User savedUser = userService.saveUser(user);

        return new ResFormat(true, 201L, "회원가입을 완료했습니다.");
    }

    @PostMapping("/signup/certification")
    public ResFormat sendCertificationCode(@RequestBody UserEmailDto userEmailDto) throws MessagingException {

        String code = mailService.sendCertificationCodeMail(userEmailDto.getEmail());

        return new ResFormat(true, 201L, code);
    }

    @PostMapping("/signup/duplicate")
    public ResFormat isNotDuplicateEmail(@RequestBody UserEmailDto userEmailDto) {

        User userByEmail = userService.findUserByEmail(userEmailDto.getEmail());
        boolean check = userByEmail == null;

        return new ResFormat(true, 200L, check);
    }

    @PostMapping("/signup/existUser")
    public ResFormat isNotExistUser(@RequestBody UserUidDto userUidDto) {

        User userByEmail = userService.findUserByUid(userUidDto.getUid());
        boolean check = userByEmail == null;

        return new ResFormat(true, 200L, check);
    }

}
