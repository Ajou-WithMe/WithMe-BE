package ajou.withme.main.controller;

import ajou.withme.main.Service.AuthService;
import ajou.withme.main.Service.MailService;
import ajou.withme.main.Service.UserService;
import ajou.withme.main.domain.Auth;
import ajou.withme.main.domain.User;
import ajou.withme.main.dto.user.LoginWithEmailDto;
import ajou.withme.main.dto.user.SignUpWithEmailDto;
import ajou.withme.main.dto.user.SignUpWithKakaoDto;
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

    @PostMapping("/mypage/changePwd")
    public ResFormat changePwd(HttpServletRequest request, @RequestParam String pwd){
        String uid = jwtTokenUtil.getSubject(request);
        User user = userService.findUserByUid(uid);

        String encodedPwd = passwordEncoder.encode(pwd);
        user.updatePwd(encodedPwd);

        userService.saveUser(user);
        return new ResFormat(true, 201L, "비밀번호 변경을 완료했습니다.");
    }

    @PostMapping("/mypage/changeName")
    public ResFormat changeName(HttpServletRequest request, @RequestParam String name){
        String uid = jwtTokenUtil.getSubject(request);
        User user = userService.findUserByUid(uid);

        user.updateName(name);

        userService.saveUser(user);
        return new ResFormat(true, 201L, "이름 변경을 완료했습니다.");
    }

    @PostMapping("/mypage/changeAddress")
    public ResFormat changeAddress(HttpServletRequest request, @RequestParam String address){
        String uid = jwtTokenUtil.getSubject(request);
        User user = userService.findUserByUid(uid);

        user.updateAddress(address);

        userService.saveUser(user);
        return new ResFormat(true, 201L, "주소 변경을 완료했습니다.");
    }

    @PostMapping("/mypage/changePhone")
    public ResFormat changePhone(HttpServletRequest request, @RequestParam String phone){
        String uid = jwtTokenUtil.getSubject(request);
        User user = userService.findUserByUid(uid);

        user.updatePhone(phone);

        userService.saveUser(user);
        return new ResFormat(true, 201L, "휴대폰 번호 변경을 완료했습니다.");
    }

}
