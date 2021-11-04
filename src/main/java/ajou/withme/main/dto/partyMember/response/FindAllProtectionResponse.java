package ajou.withme.main.dto.partyMember.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAllProtectionResponse {
    private String uid;
    private String name;
    private String profileImg;
}
