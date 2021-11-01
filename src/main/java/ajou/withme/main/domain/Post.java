package ajou.withme.main.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    //  보호자를 안만났으면 0, 만났으면 1
    private int state;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    //  지역
    private String location;

    //    활동반경
    private String activityRadius;

    //인상착의
    private String description;

    //    연락처 공개여부. 비공개0, 공개 1
    private int contact;

    //  마지막 장소
    private Double longitude;

    private Double latitude;

    private String title;

    private String content;

    //    피보호자
    @JoinColumn
    @ManyToOne
    private User protection;

    //    보호자
    @JoinColumn
    @ManyToOne
    private User guardian;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new LinkedList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostFile> postFiles = new LinkedList<>();
}
