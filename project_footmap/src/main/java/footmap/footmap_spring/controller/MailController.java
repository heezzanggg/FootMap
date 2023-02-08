package footmap.footmap_spring.controller;

import footmap.footmap_spring.dto.mailDto.Mail;
import footmap.footmap_spring.service.mailService.MailService;
import footmap.footmap_spring.service.mailService.MailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;

@Controller
@AllArgsConstructor
public class MailController {
    private final MailService mailService;

    @GetMapping("/mail")
    public String dispMail(){
        return "home/findPw";
    }
    @Transactional
    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam("memberEmail") String memberEmail, String u_name){
        System.out.println("여기왔음");
        Mail mail = mailService.createMailAndChagePassword(memberEmail,u_name);
        System.out.println("멤버 이메일" + memberEmail + u_name);
        mailService.mailSend(mail);

        return "/login";
    }
}
