package ajou.withme.main.controller;

import ajou.withme.main.Service.AuthService;
import ajou.withme.main.Service.MailService;
import ajou.withme.main.Service.UserService;
import ajou.withme.main.domain.Auth;
import ajou.withme.main.domain.User;
import ajou.withme.main.dto.user.LoginWithEmailDto;
import ajou.withme.main.dto.user.SignUpWithEmailDto;
import ajou.withme.main.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AuthService authService;

    @PostMapping("/signup/email")
    public ResFormat signUpWithEmail(@RequestBody SignUpWithEmailDto signUpWithEmailDto) {

        String encodedPwd = passwordEncoder.encode(signUpWithEmailDto.getPwd());
        User user = signUpWithEmailDto.toEntity(encodedPwd);

        User savedUser = userService.saveUser(user);

        return new ResFormat(true, 201L, savedUser);
    }

    @PostMapping("/signup/certification")
    public ResFormat sendCertificationCode(@RequestParam String email) throws MessagingException {

        String code = mailService.sendCertificationCodeMail(email);

        return new ResFormat(true, 201L, code);
    }

    @PostMapping("signup/duplicate")
    public ResFormat isNotDuplicateEmail(@RequestParam String email) {

        User userByEmail = userService.findUserByEmail(email);
        boolean check = userByEmail == null;

        return new ResFormat(true, 200L, check);
    }

    @Transactional
    @PostMapping("/login/email")
    public ResFormat loginWithEmail(@RequestBody LoginWithEmailDto loginWithEmailDto) {

        User userByEmail = userService.findUserByEmail(loginWithEmailDto.getEmail());
        boolean isLogin = passwordEncoder.matches(loginWithEmailDto.getPwd(), userByEmail.getPwd());

        if (isLogin) {
            // login 성공
            String accessToken = authService.createToken(userByEmail.getUid(), (long) (2  * 60 * 1000));
            String refreshToken = authService.createToken(userByEmail.getUid(), (long) ( 2 * 24 * 60 * 60 * 1000));

            Auth auth = authService.createAuth(refreshToken, userByEmail);

            authService.deleteAuthByUser(userByEmail);
            authService.saveAuth(auth);

            Map<String, Object> res = new LinkedHashMap<>();
            res.put("accessToken", accessToken);
            res.put("refreshToken", refreshToken);

            return new ResFormat(true, 201L, res);
        } else {
            // login 실패
            return new ResFormat(false, 400L, "로그인에 실패하였습니다.");

        }
    }
}
