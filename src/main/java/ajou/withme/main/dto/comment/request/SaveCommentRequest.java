package ajou.withme.main.dto.comment.request;

import lombok.Data;

@Data
public class SaveCommentRequest {
    private Long id;
    private String comment;
}
