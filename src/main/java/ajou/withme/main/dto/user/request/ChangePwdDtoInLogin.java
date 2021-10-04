package ajou.withme.main.dto.user.request;

import lombok.Data;

@Data
public class ChangePwdDtoInLogin {
    private String email;
    private String pwd;
}
