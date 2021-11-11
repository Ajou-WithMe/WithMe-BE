package ajou.withme.main.dto.board.response;

import ajou.withme.main.domain.Post;
import ajou.withme.main.domain.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GetUpdateBaseResponse {
    private String title;
    private String location;
    private String activityRadius;
    private String description;
    private int contact;
    private Double longitude;
    private Double latitude;
    private String content;
    private String protection;
    private int state;
    private Date createdAt;
    private List<PostFileBaseResponse> files;

    public GetUpdateBaseResponse(Post post, List<PostFileBaseResponse> postFileBaseResponses) {
        this.title = post.getTitle();
        this.location = post.getLocation();
        this.activityRadius = post.getActivityRadius();
        this.description = post.getDescription();
        this.contact = post.getContact();
        this.longitude = post.getLongitude();
        this.latitude = post.getLatitude();
        this.content = post.getContent();
        this.protection = post.getProtection().getUid();
        this.files = postFileBaseResponses;
        this.state = post.getState();
        this.createdAt = post.getCreatedAt();
    }
}
