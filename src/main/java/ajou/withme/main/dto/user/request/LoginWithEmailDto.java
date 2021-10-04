package ajou.withme.main.dto.user.request;

import lombok.Data;

@Data
public class LoginWithEmailDto {
    private String email;
    private String pwd;
}
