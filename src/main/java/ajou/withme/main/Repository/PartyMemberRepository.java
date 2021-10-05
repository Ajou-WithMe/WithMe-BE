package ajou.withme.main.Repository;

import ajou.withme.main.domain.Party;
import ajou.withme.main.domain.PartyMember;
import ajou.withme.main.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyMemberRepository extends JpaRepository<PartyMember, Long> {
    void deleteByParty(Party partyByCode);

    List<PartyMember> findAllByUser(User userByUid);


    PartyMember findByPartyAndUser(Party party, User user);

    List<PartyMember> findAllByParty(Party partyByCode);
}
