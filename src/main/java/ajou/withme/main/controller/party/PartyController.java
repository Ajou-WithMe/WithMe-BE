package ajou.withme.main.controller.party;

import ajou.withme.main.Service.PartyMemberService;
import ajou.withme.main.Service.PartyService;
import ajou.withme.main.Service.UserService;
import ajou.withme.main.domain.Party;
import ajou.withme.main.domain.PartyMember;
import ajou.withme.main.domain.User;
import ajou.withme.main.dto.party.request.CreatePartyRequest;
import ajou.withme.main.dto.party.response.CreatePartyResponse;
import ajou.withme.main.util.JwtTokenUtil;
import ajou.withme.main.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/party")
public class PartyController {

    private final PartyService partyService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final PartyMemberService partyMemberService;

    @PostMapping
    @Transactional
    public ResFormat createParty(HttpServletRequest request, @RequestBody CreatePartyRequest createPartyRequest) {
        String uid = jwtTokenUtil.getSubject(request);
        User userByUid = userService.findUserByUid(uid);

        String code = UUID.randomUUID().toString().replace("-", "").substring(0, 6);

        Party party = createPartyRequest.toEntity(code);

        partyService.saveParty(party);

        PartyMember partyMember = PartyMember.builder()
                .party(party)
                .user(userByUid)
                .type(1)
                .build();

        partyMemberService.savePartyMember(partyMember);

        return new ResFormat(true, 201L, new CreatePartyResponse(party.getName(),party.getCode()));
    }

    @DeleteMapping
    @Transactional
    public ResFormat exitParty(@RequestParam String code) {
        partyService.deletePartyByCode(code);
        return new ResFormat(true, 201L, "삭제");
    }

}
