package ajou.withme.main.controller.partyMember;

import ajou.withme.main.Service.PartyMemberService;
import ajou.withme.main.Service.PartyService;
import ajou.withme.main.Service.UserService;
import ajou.withme.main.domain.Party;
import ajou.withme.main.domain.PartyMember;
import ajou.withme.main.domain.User;
import ajou.withme.main.dto.partyMember.request.ApplyPartyMemberRequest;
import ajou.withme.main.dto.partyMember.request.ApprovalPartyMemberRequest;
import ajou.withme.main.util.JwtTokenUtil;
import ajou.withme.main.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

        PartyMember existPartyMember  = partyMemberService.findPartyMemberByPartyUser(party, user);
        if (existPartyMember != null) {
            return new ResFormat(false, 400L, "이미 신청한 그룹입니다.");

        }

        PartyMember partyMember = applyPartyMemberRequest.toEntity(user, party);
        partyMemberService.savePartyMember(partyMember);

        return new ResFormat(true, 201L, "그룹 신청을 완료했습니다.");

    }

    @PostMapping("/approval")
    @Transactional
    public ResFormat approvalPartyMember(@RequestBody ApprovalPartyMemberRequest approvalPartyMemberRequest) {
        Party partyByCode = partyService.findPartyByCode(approvalPartyMemberRequest.getCode());
        User userByUid = userService.findUserByUid(approvalPartyMemberRequest.getUid());

        if (approvalPartyMemberRequest.getApproval()) {
            PartyMember partyMemberByPartyUser = partyMemberService.findPartyMemberByPartyUser(partyByCode, userByUid);
            partyMemberByPartyUser.updateType(approvalPartyMemberRequest.getApproval());

            partyMemberService.savePartyMember(partyMemberByPartyUser);
        } else {
            partyMemberService.deletePartyMemberByPartyAndUser(partyByCode, userByUid);
        }

        return new ResFormat(true, 201L, "보호자의 참가 수락 혹은 거절을 완료했습니다.");
    }

    @DeleteMapping
    @Transactional
    public ResFormat exitParty(HttpServletRequest request, @RequestParam String code) {
        String uid = jwtTokenUtil.getSubject(request);

        Party partyByCode = partyService.findPartyByCode(code);
        User userByUid = userService.findUserByUid(uid);
        partyMemberService.deletePartyMemberByPartyAndUser(partyByCode, userByUid);

        // 보호자가 아무도 없으면 그룹 폭파 및 피보호자 계정 삭제?
        // code랑 type으로 검색해서 null이면 그룹 폭파, 피보호자 계정 삭제
        List<PartyMember> protector = partyMemberService.findAllPartyMemberByPartyAndType(partyByCode, 1);
        List<PartyMember> protectionPerson = partyMemberService.findAllPartyMemberByPartyAndType(partyByCode, 0);
        if (protector.size() == 0) {
            partyService.deletePartyByCode(code);

            for (PartyMember partyMember:
                    protectionPerson) {
                userService.deleteUser(partyMember.getUser());
            }

        }

        return new ResFormat(true, 201L, "그룹 탈퇴를 완료했습니다.");
    }

}
