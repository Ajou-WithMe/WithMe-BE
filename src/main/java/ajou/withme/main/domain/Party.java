package ajou.withme.main.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private String profile;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL)
    private List<PartyMember> partyMembers = new LinkedList<>();

    public void addPartyMember(PartyMember partyMember) {
        if (partyMembers == null) {
            this.partyMembers = new LinkedList<>();
        }
        this.partyMembers.add(partyMember);
        partyMember.setParty(this);
    }

    public void updateName(String name) {
        this.name = name;
    }
    public void updateProfile(String profile) {
        this.profile = profile;
    }

}
