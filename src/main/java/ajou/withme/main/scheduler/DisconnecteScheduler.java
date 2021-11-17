package ajou.withme.main.scheduler;

import ajou.withme.main.Service.UserOptionService;
import ajou.withme.main.domain.UserOption;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DisconnecteScheduler {

    private final UserOptionService userOptionService;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void checkDisconnect() {

        System.out.println("---------start DisconnecteScheduler---------");

        List<UserOption> all = userOptionService.findAll();

        for (UserOption userOption:
             all) {
            Timestamp userNetwork = (Timestamp) userOption.getCurrentNetwork();

            Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 1000*60*10);
            boolean isAfter = userNetwork.after(timestamp);

            if (!isAfter) {
                userOption.setIsDisconnected(1);
                userOptionService.saveUserOption(userOption);
//                알림 보내기
            }

        }

        System.out.println("---------end DisconnecteScheduler---------");


    }
}
