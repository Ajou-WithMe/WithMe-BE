package ajou.withme.main.controller.user;

import ajou.withme.main.Service.UserService;
import ajou.withme.main.domain.User;
import ajou.withme.main.dto.user.request.ChangeProfileDto;
import ajou.withme.main.dto.user.request.UserPwdDto;
import ajou.withme.main.dto.user.response.GetMyProfileResponse;
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

    // pwd랑 나머지 개인정보 변경으로 묶어서 관리
    // 현재 개인정보 보는 기능 필요함
    // 1. pwd 변경, 2. 개인정보 확인, 3. 개인정보 변경

    @PostMapping("/mypage")
    public ResFormat changeProfile(HttpServletRequest request, @RequestBody ChangeProfileDto changeProfileDto) {
        String uid = jwtTokenUtil.getSubject(request);
        User user = userService.findUserByUid(uid);

        if (changeProfileDto.getName() != null) {
            user.updateName(changeProfileDto.getName());
        }
        if (changeProfileDto.getPhone() != null) {
            user.updatePhone(changeProfileDto.getPhone());
        }
        if (changeProfileDto.getAddress() != null) {
            user.updateAddress(changeProfileDto.getAddress());
        }
        if (changeProfileDto.getProfileImg() != null) {
            user.updateProfileImg(changeProfileDto.getProfileImg());
        }

        userService.saveUser(user);
        return new ResFormat(true, 201L, "프로필 변경을 완료했습니다.");

    }

    @GetMapping("/mypage")
    public ResFormat getMyPropile(HttpServletRequest request) {
        String uid = jwtTokenUtil.getSubject(request);
        User user = userService.findUserByUid(uid);

        return new ResFormat(true, 200L, new GetMyProfileResponse(user.getName(), user.getEmail(), user.getAddress(), user.getPhone(), user.getType(), user.getProfileImg()));
    }
    
    @PostMapping("/mypage/changePwd")
    public ResFormat changePwd(HttpServletRequest request, @RequestBody UserPwdDto userPwdDto){
        String uid = jwtTokenUtil.getSubject(request);
        User user = userService.findUserByUid(uid);

        String encodedPwd = passwordEncoder.encode(userPwdDto.getPwd());
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
