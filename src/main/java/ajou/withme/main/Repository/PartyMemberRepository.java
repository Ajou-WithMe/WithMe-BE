package ajou.withme.main.Repository;

import ajou.withme.main.domain.Party;
import ajou.withme.main.domain.PartyMember;
import ajou.withme.main.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyMemberRepository extends JpaRepository<PartyMember, Long> {
    void deleteByParty(Party party);

    List<PartyMember> findAllByUser(User user);


    PartyMember findByPartyAndUser(Party party, User user);

    List<PartyMember> findAllByParty(Party party);

    void deleteByPartyAndUser(Party party, User user);

    List<PartyMember> findAllByPartyAndType(Party party, int type);
}
