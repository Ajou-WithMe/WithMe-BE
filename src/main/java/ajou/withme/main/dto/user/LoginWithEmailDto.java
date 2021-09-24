package ajou.withme.main.dto.user;

import lombok.Data;

@Data
public class LoginWithEmailDto {
    private final String email;
    private final String pwd;
}
