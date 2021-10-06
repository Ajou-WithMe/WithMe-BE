package ajou.withme.main.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PartyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Party party;

    @ManyToOne
    @JoinColumn
    private User user;

//    0:피보호자, 1:승인된 보호자, 2:미승인 보호자
    private int type;

    public void updateType(Boolean approval) {
        if (approval) {
            this.type = 1;
        }
    }
}
