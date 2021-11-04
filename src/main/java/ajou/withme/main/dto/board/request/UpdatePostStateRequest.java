package ajou.withme.main.dto.board.request;

import lombok.Data;

@Data
public class UpdatePostStateRequest {
    Long id;
    int state;
}
