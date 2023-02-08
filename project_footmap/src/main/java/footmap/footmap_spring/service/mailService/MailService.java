package footmap.footmap_spring.service.mailService;

import footmap.footmap_spring.dto.mailDto.Mail;

public interface MailService {
    Mail createMailAndChagePassword(String memberEmail,String u_name);

    String getTempPassword();

    void updatePassword(String str, String u_mail, String u_name);

    void mailSend(Mail mail);
}
