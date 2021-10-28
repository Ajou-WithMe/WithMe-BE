package ajou.withme.main.controller.protection;

import ajou.withme.main.Service.UserOptionService;
import ajou.withme.main.Service.UserService;
import ajou.withme.main.domain.User;
import ajou.withme.main.domain.UserOption;
import ajou.withme.main.dto.protection.request.ChangeSafeMoveRequest;
import ajou.withme.main.dto.protection.request.UpdateProtectionRequest;
import ajou.withme.main.util.JwtTokenUtil;
import ajou.withme.main.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/protection")
@RequiredArgsConstructor
public class ProtectionController {
    private final UserService userService;
    private final UserOptionService userOptionService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("safemove")
    public ResFormat changeSafeMove(HttpServletRequest request, @RequestBody ChangeSafeMoveRequest changeSafeMoveRequest) {
        String uid = jwtTokenUtil.getSubject(request);

        if (changeSafeMoveRequest.getUid() != null) {
            uid = changeSafeMoveRequest.getUid();
        }

        User userByUid = userService.findUserByUid(uid);

        UserOption userOption = userOptionService.findUserOptionByUser(userByUid);
        userOption.updateSafeMove(changeSafeMoveRequest.getSafemove());

        userOptionService.saveUserOption(userOption);

        return new ResFormat(true, 201L, "안심이동 설정 변경을 완료했습니다.");
    }

    @PutMapping
    public ResFormat updateProtection(@RequestBody UpdateProtectionRequest updateProtectionRequest) {
        User user = userService.findUserByUid(updateProtectionRequest.getUid());

        if (user == null) {
            return new ResFormat(false, 400L, "존재하지 않은 uid 입니다.");
        }

        if (updateProtectionRequest.getName() != null) {
            user.updateName(updateProtectionRequest.getName());
        }
        if (updateProtectionRequest.getAddress() != null) {
            user.updateAddress(updateProtectionRequest.getAddress());
        }
        if (updateProtectionRequest.getProfileImg() != null) {
            user.updateProfileImg(updateProtectionRequest.getProfileImg());
        }

        userService.saveUser(user);

        return new ResFormat(true, 201L, "프로필 변경을 완료했습니다.");
    }



}
