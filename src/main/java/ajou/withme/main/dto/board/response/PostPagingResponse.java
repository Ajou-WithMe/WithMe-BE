package ajou.withme.main.dto.board.response;

import ajou.withme.main.domain.Post;
import lombok.Data;

import java.util.Date;

@Data
public class PostPagingResponse {
    //            0. id 1.이미지 2.타이틀 3. 마지막 목격장소 4.인상착의 5. createdAt
    Long id;
    String img;
    String title;
    Double latitude;
    Double longitude;
    Date createdAt;
    String description;

    public PostPagingResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.latitude = post.getLatitude();
        this.longitude = post.getLongitude();
        this.createdAt = post.getCreatedAt();
        this.description = post.getDescription();
        this.img = null;
    }
}
