package ajou.withme.main.Service;

import ajou.withme.main.Repository.PartyMemberRepository;
import ajou.withme.main.domain.Party;
import ajou.withme.main.domain.PartyMember;
import ajou.withme.main.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartyMemberService {

    private final PartyMemberRepository partyMemberRepository;

    public PartyMember savePartyMember(PartyMember partyMember) {
        return partyMemberRepository.save(partyMember);
    }

    public void deletePartyMemberByParty(Party partyByCode) {
        partyMemberRepository.deleteByParty(partyByCode);
    }

    public List<PartyMember> findAllPartyMemberByUser(User userByUid) {
        return partyMemberRepository.findAllByUser(userByUid);
    }

    public PartyMember findPartyMemberByPartyUser(Party party, User user) {
        return partyMemberRepository.findByPartyAndUser(party, user);
    }

    public List<PartyMember> findAllPartyMemberByParty(Party partyByCode) {
        return partyMemberRepository.findAllByParty(partyByCode);
    }

    public void deletePartyMemberByPartyAndUser(Party partyByCode, User userByUid) {
        partyMemberRepository.deleteByPartyAndUser(partyByCode, userByUid);
    }

    public List<PartyMember> findAllPartyMemberByPartyAndType(Party partyByCode, int type) {
        return partyMemberRepository.findAllByPartyAndType(partyByCode, type);
    }
}
