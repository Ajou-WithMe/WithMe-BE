package ajou.withme.main.dto.user.request;

import ajou.withme.main.domain.User;
import lombok.Data;

import java.util.UUID;

@Data
public class SignUpWithEmailDto {
    private String name;
    private String email;
    private String pwd;
    private String address;
    private String phone;

    public User toEntity(String encodedPwd) {
        return User.builder()
                .email(this.email)
                .name(this.name)
                .pwd(encodedPwd)
                .address(this.address)
                .phone(this.phone)
                .profileImg(null)
                .type(0L)
                .uid(UUID.randomUUID().toString())
                .build();
    }
}
