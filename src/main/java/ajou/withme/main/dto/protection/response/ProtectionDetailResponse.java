package ajou.withme.main.dto.protection.response;

import ajou.withme.main.domain.User;
import lombok.Data;

@Data
public class ProtectionDetailResponse {
    String uid;
    String profileImg;
    String name;
    String address;
    String email;

    public ProtectionDetailResponse(User userByUid) {
        this.uid = userByUid.getUid();
        this.profileImg = userByUid.getProfileImg();
        this.name = userByUid.getName();
        this.address = userByUid.getAddress();
        this.email = userByUid.getEmail();
    }
}
