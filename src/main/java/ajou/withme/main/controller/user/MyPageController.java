package ajou.withme.main.controller.user;

import ajou.withme.main.Service.AuthService;
import ajou.withme.main.Service.MailService;
import ajou.withme.main.Service.UserService;
import ajou.withme.main.domain.User;
import ajou.withme.main.util.JwtTokenUtil;
import ajou.withme.main.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MyPageController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

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
