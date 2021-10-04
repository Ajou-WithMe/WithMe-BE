package ajou.withme.main.Service;

import ajou.withme.main.Repository.PartyRepository;
import ajou.withme.main.domain.Party;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepository;

    public Party saveParty(Party party) {
        return partyRepository.save(party);
    }

    public Party findPartyByCode(String code) {
        return partyRepository.findByCode(code);
    }

    public void deletePartyByCode(String code) {
        partyRepository.deleteByCode(code);
    }
}
