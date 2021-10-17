package ajou.withme.main.dto.party.request;

import ajou.withme.main.domain.Party;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
