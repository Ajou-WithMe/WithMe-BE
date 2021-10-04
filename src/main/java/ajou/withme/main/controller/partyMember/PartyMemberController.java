package ajou.withme.main.controller.partyMember;

import ajou.withme.main.Service.PartyMemberService;
import ajou.withme.main.Service.PartyService;
import ajou.withme.main.Service.UserService;
import ajou.withme.main.domain.Party;
import ajou.withme.main.domain.PartyMember;
import ajou.withme.main.domain.User;
import ajou.withme.main.dto.partyMember.request.ApplyPartyMemberRequest;
import ajou.withme.main.util.JwtTokenUtil;
import ajou.withme.main.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partyMember")
public class PartyMemberController {

    private final PartyService partyService;
    private final PartyMemberService partyMemberService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @PostMapping
    @Transactional
    public ResFormat applyPartyMember(HttpServletRequest request, @RequestBody ApplyPartyMemberRequest applyPartyMemberRequest) {
        String uid = jwtTokenUtil.getSubject(request);
        User user = userService.findUserByUid(uid);

        Party party = partyService.findPartyByCode(applyPartyMemberRequest.getCode());
        if (party == null) {
            return new ResFormat(false, 400L, "해당하는 code의 그룹이 없습니다.");
        }

        PartyMember partyMember = applyPartyMemberRequest.toEntity(user, party);
        partyMemberService.savePartyMember(partyMember);

        return new ResFormat(true, 201L, "그룹 신청을 완료했습니다.");

    }

}
