package footmap.footmap_spring.service.userService;

import footmap.footmap_spring.dto.userDto.User;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<User> getUserList();

    List<User> getTopuserList();

    boolean certificationupdate(String u_pw, User user, Authentication authenticationr);

    void saveUser(User user);


    Map<String, String> validateHandling(Errors errors);

    void UpdateUser(User user);

    int DoubleCheck(User user) throws Exception;

    String findid(User user) throws  Exception;

    int emailcheck(String u_mail, String u_name) throws Exception;

    int Mailcheck(User user) throws Exception;

    List<User> getTeamUserList(int u_codes);

    String getnick(int jjoku_code);

    List<User> getTeamMemberByTeamCode(int t_code);

    void updateUserRecord(int code,int assi,int goal,int cut);

}
