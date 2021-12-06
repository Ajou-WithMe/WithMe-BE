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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String pwd;

    private String address;

    private String phone;

//    type 0 : email, 1 : kakao, 2 : 피보호자
    private Long type;

    private String profileImg;

    private String uid;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PartyMember> partyMembers = new LinkedList<>();

    @OneToMany(mappedBy = "guardian", cascade = CascadeType.ALL)
    private List<Post> guardians = new LinkedList<>();

    @OneToMany(mappedBy = "protection", cascade = CascadeType.ALL)
    private List<Post> protections = new LinkedList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new LinkedList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CommentReport> commentReports = new LinkedList<>();

    public void addPartyMember(PartyMember partyMember) {
        if (partyMembers == null) {
            this.partyMembers = new LinkedList<>();
        }
        partyMembers.add(partyMember);
        partyMember.setUser(this);
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserOption userOption;

    public void updatePwd(String pwd) {
        this.pwd = pwd;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SafeZone> safeZone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<InitSafeZone> initSafeZone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ZoneLocation> zoneLocation;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<VisitOften> visitOftens;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PredictionLocation> predictionLocations;

    public void updateName(String name) {
        this.name = name;
    }

    public void updateAddress(String addr) {
        this.address = addr;
    }

    public void updatePhone(String phone) {
        this.phone = phone;
    }

    public void updateProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public UserOption initUserOptionEntity() {
        return UserOption.builder()
                .user(this)
                .isInitSafeZone(0)
                .pushAlarm(1)
                .safeMove(0)
                .distance(0D)
                .time(0L)
                .boxSize(0D)
                .isDisconnected(0)
                .build();
    }
}
