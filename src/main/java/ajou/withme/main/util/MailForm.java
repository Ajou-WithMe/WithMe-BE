package ajou.withme.main.util;

public class MailForm {
    public String getCertificationCodeMail(String code) {

        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "</head>" +
                "<body>" +
                " <div" +
                "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">" +
                "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">" +
                "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">With Me</span><br />" +
                "		<span style=\"color: #02b875\">메일인증코드</span> 안내입니다." +
                "	</h1>\n" +
                "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                "		안녕하세요.<br />" +
                "		With Me와 함께해주셔서 진심으로 감사드립니다.<br />" +
                "		아래 <b style=\"color: #02b875\">'인증 코드'</b> 를 통해 회원가입 절차를 진행해주시길 바랍니다.<br />" +
                "		감사합니다." +
                "	</p>" +
                "	<a style=\"color: #FFF; text-decoration: none; text-align: center;\"" +
                "	>" +
                "		<p" +
                "			style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #02b875; line-height: 45px; vertical-align: middle; font-size: 30px; font-weight:bold;\">" +
                "			" + code + "</p>" +
                "	</a>" +
                "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>" +
                " </div>" +
                "</body>" +
                "</html>";
    }

    public String getPwdCertificationMail(String code) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "</head>" +
                "<body>" +
                " <div" +
                "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">" +
                "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">" +
                "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">With Me</span><br />" +
                "		<span style=\"color: #02b875\">인증코드</span> 안내입니다." +
                "	</h1>\n" +
                "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                "		안녕하세요.<br />" +
                "		With Me와 함께해주셔서 진심으로 감사드립니다.<br />" +
                "		아래 <b style=\"color: #02b875\">'인증 코드'</b> 를 통해 비밀번호 찾기 절차를 진행해주시길 바랍니다.<br />" +
                "		감사합니다." +
                "	</p>" +
                "	<a style=\"color: #FFF; text-decoration: none; text-align: center;\"" +
                "	>" +
                "		<p" +
                "			style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #02b875; line-height: 45px; vertical-align: middle; font-size: 30px; font-weight:bold;\">" +
                "			" + code + "</p>" +
                "	</a>" +
                "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>" +
                " </div>" +
                "</body>" +
                "</html>";
    }

    public String missingProtection(String name) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "</head>" +
                "<body>" +
                " <div" +
                "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #e2362a; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">" +
                "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">" +
                "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">With Me</span><br />" +
                "		피보호자 <span style=\"color: #e2362a; font-weight:bold\">통신 중단</span> 안내" +
                "	</h1>\n" +
                "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                "		안녕하세요.<br />" +
                "		With Me와 함께해주셔서 진심으로 감사드립니다.<br />" +
                "		현재 <b style=\"color: #e2362a\">"+name+"</b> 님과 With Me의 통신이 이뤄지지 않습니다.<br />" +
                "		보호자님께서는 혹시 모를 상황에 대비하여 피보호자와 연락을 취하시거나, With Me 어플을 통해 마지막 위치, 자주 갔던 곳, 예상되는 최대 이동 반경을 확인해주시기 바랍니다." +
                "	</p>" +
                "	<a style=\"color: #FFF; text-decoration: none; text-align: center;\"" +
                "	>" +
                "	</a>" +
                "	<div style=\"border-top: 1px solid #e2362a; padding: 5px;\"></div>" +
                " </div>" +
                "</body>" +
                "</html>";
    }

    public String outOfSafeZone(String name) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "</head>" +
                "<body>" +
                " <div" +
                "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #e2362a; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">" +
                "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">" +
                "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">With Me</span><br />" +
                "		피보호자 <span style=\"color: #e2362a; font-weight:bold\">세이프존 이탈</span> 안내" +
                "	</h1>\n" +
                "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                "		안녕하세요.<br />" +
                "		With Me와 함께해주셔서 진심으로 감사드립니다.<br />" +
                "		현재 <b style=\"color: #e2362a\">"+name+"</b> 님의 세이프존 이탈이 확인됐습니다.<br />" +
                "		보호자님께서는 혹시 모를 상황에 대비하여 피보호자와 연락을 취하여 안전한 상황임을 확인해주시기 바랍니다." +
                "	</p>" +
                "	<a style=\"color: #FFF; text-decoration: none; text-align: center;\"" +
                "	>" +
                "	</a>" +
                "	<div style=\"border-top: 1px solid #e2362a; padding: 5px;\"></div>" +
                " </div>" +
                "</body>" +
                "</html>";
    }
}
