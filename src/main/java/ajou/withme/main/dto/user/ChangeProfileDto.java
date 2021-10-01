package ajou.withme.main.dto.user;

import lombok.Data;

@Data
public class ChangeProfileDto {
    private String name;

    private String address;

    private String phone;

    private String profileImg;
}
