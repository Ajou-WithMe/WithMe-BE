package ajou.withme.main.dto.user;

import lombok.Data;

@Data
public class ChangePwdDto {
    private final String email;
    private final String pwd;
}
