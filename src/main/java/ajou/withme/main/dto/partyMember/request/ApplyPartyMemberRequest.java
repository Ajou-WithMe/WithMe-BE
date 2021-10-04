package ajou.withme.main.dto.partyMember.request;

import ajou.withme.main.domain.Party;
import ajou.withme.main.domain.PartyMember;
import ajou.withme.main.domain.User;
import lombok.Data;

@Data
public class ApplyPartyMemberRequest {
    private String code;

    public PartyMember toEntity(User user, Party party) {
        return PartyMember.builder()
                .type(2)
                .user(user)
                .party(party)
                .build();
    }
}
