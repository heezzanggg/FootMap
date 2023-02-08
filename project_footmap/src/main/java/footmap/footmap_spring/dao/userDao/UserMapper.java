package footmap.footmap_spring.dao.userDao;

import footmap.footmap_spring.dto.userDto.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<User> getTeamUserList(int u_codes);

    List<User> getUserList();

    List<User> getTopuserList();

    void saveUser(User user);

    User getUserAccount(String u_id);//이친구는 CustomUserDetailsService로 이동

    void UpdateUser(User user);

    int DoubleCheck(User user);

    String findid(User user);

    int emailcheck(String u_mail, String u_name);

    void updateUser(String str, String u_mail, String u_name);

    int MailCheck(User user);

    String getnick(int jjoku_code);

    List<User> getTeamMemberByTeamCode(int t_code);

    void updateUserRecord(int code,int assi,int goal,int cut);
}