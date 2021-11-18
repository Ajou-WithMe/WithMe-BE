package ajou.withme.main.controller.notification;

import ajou.withme.main.Service.MailService;
import ajou.withme.main.Service.PartyMemberService;
import ajou.withme.main.Service.UserService;
import ajou.withme.main.domain.Party;
import ajou.withme.main.domain.PartyMember;
import ajou.withme.main.domain.User;
import ajou.withme.main.util.JwtTokenUtil;
import ajou.withme.main.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final MailService mailService;
    private final PartyMemberService partyMemberService;

    @PostMapping("/out")
    public ResFormat outOfSafeZone(HttpServletRequest request) throws MessagingException {
        String uid = jwtTokenUtil.getSubject(request);
        User user = userService.findUserByUid(uid);

        List<PartyMember> allPartyMemberByUser = partyMemberService.findAllPartyMemberByUser(user);
        Party party = allPartyMemberByUser.get(0).getParty();
        List<PartyMember> allPartyMemberByPartyAndType = partyMemberService.findAllPartyMemberByPartyAndType(party, 1);

        if (user.getType() == 2) {
            for (PartyMember partyMember:
                    allPartyMemberByPartyAndType) {
                User guardian = partyMember.getUser();
                if (guardian.getEmail() == null) {
                    continue;
                }
                mailService.sendOutOfSafeZone(guardian.getEmail(),user.getName());
            }
        }

        return new ResFormat(true, 201L, "세이프존 이탈 알림 메일을 전송했습니다.");
    }
}
