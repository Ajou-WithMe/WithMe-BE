package ajou.withme.main.Service;

import ajou.withme.main.Repository.PartyMemberRepository;
import ajou.withme.main.domain.PartyMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyMemberService {

    private final PartyMemberRepository partyMemberRepository;

    public PartyMember savePartyMember(PartyMember partyMember) {
        return partyMemberRepository.save(partyMember);
    }

}
