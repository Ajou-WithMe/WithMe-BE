package ajou.withme.main.controller;

import ajou.withme.main.Service.AuthService;
import ajou.withme.main.Service.MailService;
import ajou.withme.main.Service.UserService;
import ajou.withme.main.domain.Auth;
import ajou.withme.main.domain.User;
import ajou.withme.main.dto.user.LoginWithEmailDto;
import ajou.withme.main.dto.user.SignUpWithEmailDto;
import ajou.withme.main.util.JwtTokenUtil;
import ajou.withme.main.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;

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

    @PostMapping("/signup/duplicate")
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
            String accessToken = authService.createToken(userByEmail.getUid(), (2L * 60 * 60 * 1000));
            String refreshToken = authService.createToken(userByEmail.getUid(), (14L * 24 * 60 * 60 * 1000));

            Auth auth = authService.createAuth(accessToken, refreshToken, userByEmail);

            authService.deleteAuthByUser(userByEmail);
            authService.saveAuth(auth);

            return new ResFormat(true, 201L, accessToken);
        } else {
            // login 실패
            return new ResFormat(false, 400L, "로그인에 실패하였습니다.");

        }
    }

    @PostMapping("/findPwd")
    public ResFormat findPwdCertification(HttpServletRequest request, @RequestParam String email) throws MessagingException {
//        String uid = jwtTokenUtil.getSubject(request);
//        User user = userService.findUserByUid(uid);

        User userByEmail = userService.findUserByEmail(email);

        if (userByEmail == null) {
            return new ResFormat(false, 400L, "등록되지 않은 이메일입니다.");
        }

        String code = mailService.sendPwdCertification(email);

        return new ResFormat(true, 201L, code);
    }

    @PostMapping("/findPwd/changePwd")
    public ResFormat changePwdAfterCertification(@RequestParam String email, @RequestParam String pwd) throws MessagingException {

        User userByEmail = userService.findUserByEmail(email);
        if (userByEmail == null) {
            return new ResFormat(false, 400L, "등록되지 않은 이메일입니다.");
        }

        String encodedPwd = passwordEncoder.encode(pwd);
        userByEmail.updatePwd(encodedPwd);

        userService.saveUser(userByEmail);
        return new ResFormat(true, 201L, "비밀번호 변경을 완료했습니다.");
    }
}
