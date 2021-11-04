package ajou.withme.main.dto.board.response;

import ajou.withme.main.domain.Post;
import ajou.withme.main.domain.PostFile;
import lombok.Data;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
public class GetPostDetailResponse {
    Long id;
    List<String> files;
    String title;
    Date createdAt;
    String content;
    String activityRadius;
    String description;
    String name;
    String contact;
    Double longitude;
    Double latitude;

    public GetPostDetailResponse(Post post, List<String> files) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.createdAt = post.getCreatedAt();
        this.content = post.getContent();
        this.activityRadius = post.getActivityRadius();
        this.description = post.getDescription();
        this.name = post.getProtection().getName();

        System.out.println("post.getContact() = " + post.getContact());
        if (post.getContact() == 1) {
            this.contact = post.getGuardian().getPhone();
        } else {
            this.contact = null;
        }

        this.latitude = post.getLatitude();
        this.longitude = post.getLongitude();

        this.files = files;

    }

}
