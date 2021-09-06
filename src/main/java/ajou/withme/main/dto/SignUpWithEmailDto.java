package ajou.withme.main.dto;

import ajou.withme.main.domain.User;
import lombok.Data;

@Data
public class SignUpWithEmailDto {
    private String name;
    private String email;
    private String pwd;
    private String address;

    public User toEntity(String saltedPwd) {
        return User.builder()
                .email(this.email)
                .name(this.name)
                .pwd(saltedPwd)
                .address(this.address)
                .build();
    }
}
