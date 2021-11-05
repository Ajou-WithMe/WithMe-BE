package ajou.withme.main.dto.comment.response;

import ajou.withme.main.domain.Comment;
import lombok.Data;

import java.util.Date;

@Data
public class GetAllCommentResponse {
    Long id;
    boolean delete;
    Date createdAt;
    String name;
    String profileImg;
    String comment;

    public GetAllCommentResponse(Comment comment) {
        this.id = comment.getId();
        this.createdAt = comment.getCreatedAt();
        this.name = comment.getUser().getName();
        this.profileImg = comment.getUser().getProfileImg();
        this.comment = comment.getComment();
        this.delete = false;
    }
}
