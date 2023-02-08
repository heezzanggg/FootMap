package footmap.footmap_spring.service.userService;


import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import footmap.footmap_spring.dao.userDao.UserMapper;
import footmap.footmap_spring.dto.userDto.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getUserList() {
        return userMapper.getUserList();
    }

    @Override
    public List<User> getTopuserList() {
        return userMapper.getTopuserList();
    }

    @Transactional
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        /* 유효성 검사에 실패한 필드 목록을 받음 */
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    @Override
    public void UpdateUser(User user) {
        log.info("현재 로그인된 아이디=========" + user.getU_id());
        log.info("바뀐 비밀번호" + user.getU_pw());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setU_pw(passwordEncoder.encode(user.getU_pw()));
        user.setU_nick(user.getU_nick());
        user.setU_tel(user.getU_tel());
        user.setU_mail(user.getU_mail());
        log.info("혹시모르니깐" + passwordEncoder.encode(user.getU_pw()));
        log.info("유조ㅇ비니당ㄴ운야즃ㅇ류@@@@@@@@@@" + user);
        userMapper.UpdateUser(user);
    }

    @Override
    public int DoubleCheck(User user) throws Exception {
        try {
            System.out.println("serviceImpl user값 입니다." + user);
            int result = userMapper.DoubleCheck(user);
            System.out.println("serviceIMpl 결과값 입니다. :  " + result);
            return result;
        } catch (Exception e) {
            System.out.println("DOubleCheck 오류내용 " + e.getMessage());
            throw e;
        }
    }

    @Override
    public String findid(User user) throws Exception {
        try {
            System.out.println("serviceImpl user 값입니다. id=" + user);
            String id = userMapper.findid(user);
            return id;
        } catch (Exception e) {
            System.out.println("findid 오류내용" + e.getMessage());
            throw e;
        }
    }

    @Override
    public int emailcheck(String u_mail, String u_name) throws Exception {
        try {
            int result = userMapper.emailcheck(u_mail, u_name);
            System.out.println("서비스임플 데이터체크" + u_mail + u_name);
            System.out.println("서비스임플 이메일체크" + result);
            return result;
        } catch (Exception e) {
            System.out.println("emailcheck 오류내용" + e.getMessage());
            throw e;
        }
    }

    @Override
    public int Mailcheck(User user) throws Exception {
        try {
            int result = userMapper.MailCheck(user);
            return result;
        } catch (Exception e) {
            System.out.println("메일중복확인 오류" + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<User> getTeamUserList(int u_codes) {
        List<User> getTeamUserList = userMapper.getTeamUserList(u_codes);
        return getTeamUserList;
    }

    @Override
    public String getnick(int jjoku_code) {
        System.out.println("여기까지 옴ㄴㄴㄴㄴㄴㄴㄴ");
        System.out.println(jjoku_code);
        String getnick = userMapper.getnick(jjoku_code);
        System.out.println("여기까지옴");
        return getnick;
    }

    @Override
    public boolean certificationupdate(String u_pw, User user, Authentication authentication){
        User users = (User) authentication.getPrincipal();
        log.info("현재비밀번호" + users.getU_pw());
        log.info("받은 비밀번호" +u_pw);
        log.info("패스워드 채크" + passwordEncoder.matches(u_pw,users.getU_pw()));
        if (passwordEncoder.matches(u_pw, user.getU_pw())){
            return true;
        }else {
            return false;
        }
    }


    @Transactional
    public void saveUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setU_pw(passwordEncoder.encode(user.getPassword()));
        user.setUser_auth("ROLE_USER");

        userMapper.saveUser(user);
    }

    @Override
    public List<User> getTeamMemberByTeamCode(int t_code) {
        List<User> getTeamMemberByTeamCode = userMapper.getTeamMemberByTeamCode(t_code);
        return getTeamMemberByTeamCode;
    }

    @Override
    public void updateUserRecord(int code,int assi,int goal,int cut) {
        userMapper.updateUserRecord(code,assi,goal,cut);
    }







}