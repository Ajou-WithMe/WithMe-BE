package ajou.withme.main.dto.user;

import ajou.withme.main.domain.User;
import lombok.Data;

import java.util.UUID;

@Data
public class SignUpWithEmailDto {
    private final String name;
    private final String email;
    private final String pwd;
    private final String address;
    private final String phone;

    public User toEntity(String encodedPwd) {
        return User.builder()
                .email(this.email)
                .name(this.name)
                .pwd(encodedPwd)
                .address(this.address)
                .phone(this.phone)
                .type(0L)
                .uid(UUID.randomUUID().toString())
                .build();
    }
}
