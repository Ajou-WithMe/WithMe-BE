package ajou.withme.main.dto.partyMember.request;

import lombok.Data;

@Data
public class ApprovalPartyMemberRequest {
    private String code;
    private String uid;
    private Boolean approval;
}
