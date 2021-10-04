package ajou.withme.main.dto.party.request;

import ajou.withme.main.domain.Party;
import lombok.Data;

@Data
public class CreatePartyRequest {

    private String profile;

    private String name;

    public Party toEntity(String code) {
        return Party.builder()
                .code(code)
                .profile(this.profile)
                .name(name)
                .build();
    }
}
