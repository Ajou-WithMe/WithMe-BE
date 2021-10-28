package ajou.withme.main.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginWithEmailDto {
    private String email;
    private String pwd;
}
