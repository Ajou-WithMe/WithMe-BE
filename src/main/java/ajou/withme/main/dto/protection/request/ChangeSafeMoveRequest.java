package ajou.withme.main.dto.protection.request;

import lombok.Data;

@Data
public class ChangeSafeMoveRequest {
//    1 트루, 0 펄스
    int safemove;

    String uid;
}
