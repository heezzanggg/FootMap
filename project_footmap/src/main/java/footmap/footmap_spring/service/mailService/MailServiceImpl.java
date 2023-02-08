package footmap.footmap_spring.service.mailService;

import footmap.footmap_spring.dao.userDao.UserMapper;
import footmap.footmap_spring.dto.mailDto.Mail;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService{
    @Autowired
    UserMapper userMapper;
    private JavaMailSender mailSender;

    @Override
    public Mail createMailAndChagePassword(String memberEmail,String u_name) {
        String str = getTempPassword();
        Mail mail = new Mail();
        mail.setAddress(memberEmail);
        mail.setTitle("Footmap 임시비밀번호 안내 이메일 입니다.");
        mail.setMessage("안녕하세요. Footmap 임시비밀번호 안내 관련 이메일 입니다.\r"
        +"회원님의 임시 비밀번호는 " + str + "입니다.\r" + "로그인 후에 반드시 비밀번호를 변경 해주세요.");
        updatePassword(str,memberEmail,u_name);
        return mail;
    }

    @Override
    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    @Override
    public void updatePassword(String str, String u_mail, String u_name){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        str = passwordEncoder.encode(str);
        userMapper.updateUser(str,u_mail,u_name);
    }

    @Override
    public void mailSend(Mail mail){
        System.out.println("전송완료!@");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getAddress());
        message.setSubject(mail.getTitle());
        message.setText(mail.getMessage());
        message.setFrom("seokjun6266@gmail.com");
        message.setReplyTo("seokjun6266@gmail.com");
        System.out.println("message" + message);
        mailSender.send(message);
    }
}
