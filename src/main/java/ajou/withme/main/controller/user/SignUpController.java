package ajou.withme.main.controller.user;

import ajou.withme.main.Service.*;
import ajou.withme.main.domain.Party;
import ajou.withme.main.domain.PartyMember;
import ajou.withme.main.domain.User;
import ajou.withme.main.domain.UserOption;
import ajou.withme.main.dto.user.request.*;
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
    private final UserOptionService userOptionService;
    private final PartyService partyService;
    private final PartyMemberService partyMemberService;

    @PostMapping("/signup/email")
    public ResFormat signUpWithEmail(@RequestBody SignUpWithEmailDto signUpWithEmailDto) {

        User userByEmail = userService.findUserByEmail(signUpWithEmailDto.getEmail());
        if (userByEmail != null) {
            return new ResFormat(false, 400L, "이미 회원가입된 이메일입니다.");
        }

        String encodedPwd = passwordEncoder.encode(signUpWithEmailDto.getPwd());
        User user = signUpWithEmailDto.toEntity(encodedPwd);
        User savedUser = userService.saveUser(user);

        UserOption userOption = user.initUserOptionEntity();
        userOptionService.saveUserOption(userOption);

        return new ResFormat(true, 201L, "회원가입을 완료했습니다.");
    }

    @PostMapping("/signup/kakao")
    public ResFormat signUpWithKakao(@RequestBody SignUpWithKakaoDto signUpWithKakaoDto) {

        User user = signUpWithKakaoDto.toEntity();
        User savedUser = userService.saveUser(user);

        UserOption userOption = user.initUserOptionEntity();
        userOptionService.saveUserOption(userOption);

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

    @PostMapping("/signup/protection")
    public ResFormat signupProtection(@RequestBody SignUpProtectionRequest signUpProtectionRequest) {
//        아이디 중복체크
//        비밀번호 암호화
        User userByEmail = userService.findUserByEmail(signUpProtectionRequest.getEmail());
        if (userByEmail != null) {
            return new ResFormat(false, 400L, "이미 회원가입된 아이디입니다.");
        }
        String encodedPwd = passwordEncoder.encode(signUpProtectionRequest.getPwd());

        User newUser = signUpProtectionRequest.toEntity(encodedPwd);
        User savedUser = userService.saveUser(newUser);

        UserOption userOption = newUser.initUserOptionEntity();
        userOptionService.saveUserOption(userOption);
        
//        그룹에 추가
        Party partyByCode = partyService.findPartyByCode(signUpProtectionRequest.getCode());

        PartyMember partyMember = new PartyMember(partyByCode, newUser, 0);
        partyMemberService.savePartyMember(partyMember);

        return new ResFormat(true, 201L, "피보호자 계정 생성을 완료했습니다.");


    }

}
