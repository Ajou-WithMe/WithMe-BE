package ajou.withme.main.dto.party.response;

import lombok.Data;

import java.util.List;

@Data
public class GetPartyDetailResponse {
    private final String name;
    private final String profile;
    private final String code;

    private final List<PartyDetailUserResponse> protector;
    private final List<PartyDetailProtectionResponse> protectionPerson;
}
