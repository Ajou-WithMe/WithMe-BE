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

    private int safeMove;

    private int pushAlarm;

    // 세이프존 테이블이 2개니까, 신규 유저인지 확인하는 칼럼. true(1)면 신규유저, false면 새로 safe존을 만든것
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
