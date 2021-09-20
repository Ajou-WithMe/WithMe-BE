package ajou.withme.main.controller.user;

import ajou.withme.main.Service.AuthService;
import ajou.withme.main.Service.MailService;
import ajou.withme.main.Service.UserService;
import ajou.withme.main.domain.Auth;
import ajou.withme.main.domain.User;
import ajou.withme.main.dto.user.LoginWithEmailDto;
import ajou.withme.main.dto.user.LoginWithKakaoDto;
import ajou.withme.main.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AuthService authService;

    @Transactional
    @PostMapping("/login/email")
    public ResFormat loginWithEmail(@RequestBody LoginWithEmailDto loginWithEmailDto) {

        User userByEmail = userService.findUserByEmail(loginWithEmailDto.getEmail());
        if (userByEmail == null) {
            return new ResFormat(false, 400L, "등록되지 않은 이메일입니다.");
        }
        boolean isLogin = passwordEncoder.matches(loginWithEmailDto.getPwd(), userByEmail.getPwd());

        if (isLogin) {
            // login 성공
            String accessToken = authService.createToken(userByEmail.getUid(), (2L * 60 * 60 * 1000));
            String refreshToken = authService.createToken(userByEmail.getUid(), (30L * 24 * 60 * 60 * 1000));

            Auth auth = authService.createAuth(accessToken, refreshToken, userByEmail);

            authService.deleteAuthByUser(userByEmail);
            authService.saveAuth(auth);

            return new ResFormat(true, 201L, accessToken);
        } else {
            // login 실패
            return new ResFormat(false, 400L, "비밀번호가 일치하지 않습니다.");

        }
    }

    @Transactional
    @PostMapping("/login/kakao")
    public ResFormat loginWithKakao(@RequestBody LoginWithKakaoDto loginWithKakaoDto) {

        User userByUid = userService.findUserByUid(loginWithKakaoDto.getUid());
        if (userByUid == null) {
            return new ResFormat(false, 400L, "등록되지 않은 이메일입니다.");
        }

        // login 성공
        String accessToken = authService.createToken(loginWithKakaoDto.getUid(), (2L * 60 * 60 * 1000));
        String refreshToken = authService.createToken(loginWithKakaoDto.getUid(), (30L * 24 * 60 * 60 * 1000));

        Auth auth = authService.createAuth(accessToken, refreshToken, userByUid);

        authService.deleteAuthByUser(userByUid);
        authService.saveAuth(auth);

        return new ResFormat(true, 201L, accessToken);
    }

    @PostMapping("/login/findPwd")
    public ResFormat findPwdCertification(@RequestParam String email) throws MessagingException {
        User userByEmail = userService.findUserByEmail(email);

        if (userByEmail == null) {
            return new ResFormat(false, 400L, "등록되지 않은 이메일입니다.");
        }

        String code = mailService.sendPwdCertification(email);

        return new ResFormat(true, 201L, code);
    }

    @PostMapping("/login/findEmail")
    public ResFormat findEmail(@RequestParam String name, @RequestParam String phone){
        User userByEmail = userService.findUserByNamePhone(name, phone);

        if (userByEmail == null) {
            return new ResFormat(false, 400L, "조회에 실패했습니다.");
        }
        return new ResFormat(true, 201L, userByEmail.getEmail());
    }

    @PostMapping("/login/findPwd/changePwd")
    public ResFormat changePwdAfterCertification(@RequestParam String email, @RequestParam String pwd){

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
