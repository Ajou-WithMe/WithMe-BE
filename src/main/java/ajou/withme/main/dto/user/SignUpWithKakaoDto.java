package ajou.withme.main.dto.user;

import ajou.withme.main.domain.User;
import lombok.Data;

@Data
public class SignUpWithKakaoDto {
    private String name;
    private String address;
    private String phone;
    private String uid;

    public User toEntity() {
        return User.builder()
                .name(this.name)
                .address(this.address)
                .phone(this.phone)
                .type(1L)
                .uid(this.uid)
                .profileImg(null)
                .build();
    }
}
