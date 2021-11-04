package ajou.withme.main.dto.board.request;

import ajou.withme.main.domain.Post;
import ajou.withme.main.domain.User;
import lombok.Data;

import java.util.List;

@Data
public class SavePostRequest {
    private String title;
    private String location;
    private String activityRadius;
    private String description;
    private int contact;
    private Double longitude;
    private Double latitude;
    private String content;
    private String protection;
    private List<String> files;

    public Post toEntity(User protection, User guardian) {
        return Post.builder()
                .title(this.title)
                .location(this.location)
                .activityRadius(activityRadius)
                .description(this.description)
                .contact(this.contact)
                .longitude(this.longitude)
                .latitude(this.latitude)
                .content(this.content)
                .guardian(guardian)
                .protection(protection)
                .state(0)
                .build();

    }
}
