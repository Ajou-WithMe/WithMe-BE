package ajou.withme.main.dto.party.response;

import lombok.Data;

@Data
public class PartyDetailUserResponse {
    // 프로필, 이름, uid
    private final String name;
    private final String profile;
    private final String uid;
}
