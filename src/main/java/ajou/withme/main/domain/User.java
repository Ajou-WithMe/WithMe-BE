package ajou.withme.main.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
