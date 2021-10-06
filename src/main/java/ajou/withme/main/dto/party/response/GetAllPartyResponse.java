package ajou.withme.main.dto.party.response;

import lombok.Data;

@Data
public class GetAllPartyResponse {
    private final String name;
    private final String profile;
    private final String code;
    private final int type;

}
