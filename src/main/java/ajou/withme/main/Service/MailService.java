package ajou.withme.main.Service;

import ajou.withme.main.util.MailForm;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String sendCertificationCodeMail(String toEmail) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        int start = (int) (Math.random() * 27);
        String code = UUID.randomUUID().toString().replace("-", "").substring(start, start + 6);

        MailForm mailForm = new MailForm();
        String mailContent = mailForm.getCertificationCodeMail(code);

        helper.setFrom("WithMe"); //보내는사람
        helper.setTo(toEmail); //받는사람
        helper.setSubject("[WithMe] 회원가입 이메일 인증코드"); //메일제목
        helper.setText(mailContent, true); //ture넣을경우 html


        javaMailSender.send(mimeMessage);

        return code;
    }

    public String sendPwdCertification(String toEmail) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        int start = (int) (Math.random() * 27);
        String code = UUID.randomUUID().toString().replace("-", "").substring(start, start + 6);

        MailForm mailForm = new MailForm();
        String mailContent = mailForm.getPwdCertificationMail(code);

        helper.setFrom("WithMe"); //보내는사람
        helper.setTo(toEmail); //받는사람
        helper.setSubject("[WithMe] 비밀번호 찾기 인증코드"); //메일제목
        helper.setText(mailContent, true); //ture넣을경우 html


        javaMailSender.send(mimeMessage);

        return code;
    }

    public void sendDisconnectedUser(String toEmail, String name) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        MailForm mailForm = new MailForm();
        String mailContent = mailForm.missingProtection(name);

        helper.setFrom("WithMe"); //보내는사람
        helper.setTo(toEmail); //받는사람
        helper.setSubject("[WithMe] "+name+"님의 통신이 끊어졌습니다."); //메일제목
        helper.setText(mailContent, true); //ture넣을경우 html

        javaMailSender.send(mimeMessage);

    }

    public void sendOutOfSafeZone(String toEmail, String name) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        MailForm mailForm = new MailForm();
        String mailContent = mailForm.outOfSafeZone(name);

        helper.setFrom("WithMe"); //보내는사람
        helper.setTo(toEmail); //받는사람
        helper.setSubject("[WithMe] "+name+"님이 세이프존을 이탈했습니다."); //메일제목
        helper.setText(mailContent, true); //ture넣을경우 html

        javaMailSender.send(mimeMessage);

    }
}
