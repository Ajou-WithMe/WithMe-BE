package ajou.withme.main.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginWithEmailDto {
    private String email;
    private String pwd;
}
