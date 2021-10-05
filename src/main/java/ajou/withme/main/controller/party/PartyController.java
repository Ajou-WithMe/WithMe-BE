package ajou.withme.main.controller.party;

import ajou.withme.main.Service.PartyMemberService;
import ajou.withme.main.Service.PartyService;
import ajou.withme.main.Service.UserService;
import ajou.withme.main.domain.Party;
import ajou.withme.main.domain.PartyMember;
import ajou.withme.main.domain.User;
import ajou.withme.main.dto.party.request.CreatePartyRequest;
import ajou.withme.main.dto.party.request.UpdatePartyRequest;
import ajou.withme.main.dto.party.response.*;
import ajou.withme.main.util.JwtTokenUtil;
import ajou.withme.main.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
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

        while (partyService.findPartyByCode(code) != null) {
            code = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        }

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

    @PutMapping
    public ResFormat updateParty(@RequestBody UpdatePartyRequest updatePartyRequest) {
        Party partyByCode = partyService.findPartyByCode(updatePartyRequest.getCode());

        if (partyByCode == null) {
            return new ResFormat(false, 400L, "해당하는 code의 그룹이 없습니다.");
        }

        if (updatePartyRequest.getProfile() != null) {
            partyByCode.updateProfile(updatePartyRequest.getProfile());            
        }

        if (updatePartyRequest.getName() != null) {
            partyByCode.updateName(updatePartyRequest.getName());
        }

        partyService.saveParty(partyByCode);

        return new ResFormat(true, 201L, "그룹 업데이트를 완료했습니다.");
    }

    @GetMapping
    public ResFormat getParty(@RequestParam String code) {
        Party partyByCode = partyService.findPartyByCode(code);

        if (partyByCode == null) {
            return new ResFormat(false, 400L, "해당하는 code의 그룹이 없습니다.");
        }

        return new ResFormat(true, 200L, new GetPartyResponse(partyByCode.getName(), partyByCode.getProfile(), partyByCode.getCode()));
    }

    @GetMapping("/all")
    public ResFormat getAllParty(HttpServletRequest request) {
        String uid = jwtTokenUtil.getSubject(request);
        User userByUid = userService.findUserByUid(uid);

        List<PartyMember> allPartyMemberByUser = partyMemberService.findAllPartyMemberByUser(userByUid);

        List<GetAllPartyResponse> getAllPartyResponses = new LinkedList<>();

        for (PartyMember partyMember:
             allPartyMemberByUser) {
            Party curParty = partyMember.getParty();
            getAllPartyResponses.add(new GetAllPartyResponse(curParty.getName(), curParty.getProfile(), curParty.getCode(), partyMember.getType()));
        }


        return new ResFormat(true, 200L, getAllPartyResponses);
    }

    @GetMapping("/detail")
    public ResFormat getPartyDetail(@RequestParam String code) {
        Party partyByCode = partyService.findPartyByCode(code);
        if (partyByCode == null) {
            return new ResFormat(false, 400L, "해당하는 code의 그룹이 없습니다.");
        }
        List<PartyMember> allPartyMemberByParty = partyMemberService.findAllPartyMemberByParty(partyByCode);

        // 프로필, 이름, uid
        List<PartyDetailUserResponse> protector = new LinkedList<>();
        List<PartyDetailUserResponse> protectionPerson = new LinkedList<>();

        for (PartyMember partyMember:
                allPartyMemberByParty) {

            User curUser = partyMember.getUser();

            if (partyMember.getType() == 0) {
                protectionPerson.add(new PartyDetailUserResponse(curUser.getName(),curUser.getProfileImg(),curUser.getUid()));
            } else {
                protector.add(new PartyDetailUserResponse(curUser.getName(),curUser.getProfileImg(),curUser.getUid()));
            }
        }

        return new ResFormat(true, 200L, new GetPartyDetailResponse(partyByCode.getName(),partyByCode.getProfile(),partyByCode.getCode(), protector, protectionPerson));

    }

}
