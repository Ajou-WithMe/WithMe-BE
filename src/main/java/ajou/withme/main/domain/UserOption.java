package ajou.withme.main.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    1이 켜진거, 0은 꺼진거
    private int safeMove;

//    1이 켜진거, 0은 꺼진거
    private int pushAlarm;

    // 세이프존 테이블이 2개니까, 0이 신규유저 1인 신규 ㄴㄴ
    private int isInitSafeZone;
    
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date currentNetwork;

    private Long time;
    private Double distance;

    private Double boxSize;

    //    좌측 최상단의 x,y좌표
    private Double xTemp;
    private Double yTemp;

    @JoinColumn
    @OneToOne
    User user;

    public void updateSafeMove(int safemode) {
        System.out.println("safemode = " + safemode);
        this.safeMove = safemode;
        System.out.println("this.safeMove = " + this.safeMove);
    }
}
