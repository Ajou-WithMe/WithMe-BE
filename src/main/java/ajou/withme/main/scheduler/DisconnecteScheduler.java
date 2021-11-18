package ajou.withme.main.scheduler;

import ajou.withme.main.Service.MailService;
import ajou.withme.main.Service.PartyMemberService;
import ajou.withme.main.Service.UserOptionService;
import ajou.withme.main.domain.Party;
import ajou.withme.main.domain.PartyMember;
import ajou.withme.main.domain.User;
import ajou.withme.main.domain.UserOption;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DisconnecteScheduler {

    private final UserOptionService userOptionService;
    private final MailService mailService;
    private final PartyMemberService partyMemberService;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void checkDisconnect() throws MessagingException {

        System.out.println("---------start DisconnecteScheduler---------");

        List<UserOption> all = userOptionService.findAll();

        for (UserOption userOption:
             all) {
            User user = userOption.getUser();
            List<PartyMember> allPartyMemberByUser = partyMemberService.findAllPartyMemberByUser(user);
            Party party = allPartyMemberByUser.get(0).getParty();
            List<PartyMember> allPartyMemberByPartyAndType = partyMemberService.findAllPartyMemberByPartyAndType(party, 1);


            Timestamp userNetwork = (Timestamp) userOption.getCurrentNetwork();

            Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 1000*60*10);
            boolean isAfter = userNetwork.after(timestamp);

            if (user.getType()==2 && !isAfter) {
                userOption.setIsDisconnected(1);
                userOptionService.saveUserOption(userOption);
//                알림 보내기
                for (PartyMember partyMember:
                     allPartyMemberByPartyAndType) {
                    User guardian = partyMember.getUser();
                    if (guardian.getEmail() == null) {
                        continue;
                    }
                    mailService.sendDisconnectedUser(guardian.getEmail(),user.getName());
                }
            }

        }

        System.out.println("---------end DisconnecteScheduler---------");


    }
}
