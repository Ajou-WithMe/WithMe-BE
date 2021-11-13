package ajou.withme.main.dto.protection.request;

import lombok.Data;

@Data
public class UpdateProtectionRequest {
    String uid;
    String profileImg;
    String name;
    String address;
    String pwd;
}
