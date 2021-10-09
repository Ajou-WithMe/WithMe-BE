package ajou.withme.main.dto.user.request;

import ajou.withme.main.domain.User;
import lombok.Data;

import java.util.UUID;

@Data
public class SignUpProtectionRequest {
    private String name;
    private String email;
    private String address;
    private String pwd;
    private String code;
    private String profile;

    public User toEntity(String pwd) {
        return User.builder()
                .profileImg(this.profile)
                .phone(null)
                .type(2L)
                .name(this.name)
                .pwd(pwd)
                .email(this.email)
                .address(this.address)
                .uid(UUID.randomUUID().toString())
                .build();
    }
}
