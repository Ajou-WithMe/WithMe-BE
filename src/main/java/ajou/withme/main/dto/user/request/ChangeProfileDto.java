package ajou.withme.main.dto.user.request;

import lombok.Data;

@Data
public class ChangeProfileDto {
    private String name;

    private String address;

    private String phone;

    private String profileImg;
}
