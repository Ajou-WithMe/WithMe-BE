package ajou.withme.main.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetMyProfileResponse {
    private String name;

    private String email;

    private String address;

    private String phone;

    //    type 0 : email, 1 : kakao, 2 : 피보호자
    private Long type;

    private String profileImg;
}
