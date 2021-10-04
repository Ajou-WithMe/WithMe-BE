package ajou.withme.main.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String pwd;

    private String address;

    private String phone;

//    type 0 : email, 1 : kakao, 2 : 피보호자
    private Long type;

    private String profileImg;

    private String uid;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PartyMember> partyMembers = new LinkedList<>();

    public void addPartyMember(PartyMember partyMember) {
        if (partyMembers == null) {
            this.partyMembers = new LinkedList<>();
        }
        partyMembers.add(partyMember);
        partyMember.setUser(this);
    }

    public void updatePwd(String pwd) {
        this.pwd = pwd;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateAddress(String addr) {
        this.address = addr;
    }

    public void updatePhone(String phone) {
        this.phone = phone;
    }

    public void updateProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
