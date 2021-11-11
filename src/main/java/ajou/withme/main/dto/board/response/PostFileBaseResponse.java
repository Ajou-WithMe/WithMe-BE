package ajou.withme.main.dto.board.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostFileBaseResponse {
    String file;
    String url;
}
