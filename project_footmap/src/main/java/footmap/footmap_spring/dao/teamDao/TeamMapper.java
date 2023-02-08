package footmap.footmap_spring.dao.teamDao;

import footmap.footmap_spring.dto.teamDto.team;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeamMapper {
    List<team> getTeamList();

    List<team> getTopTeam();

    List<team> getTeaminUser(String u_code);

    List<team> chkCap(String u_code);

    void createTeam(String t_name, String t_intro, String t_stadium, String uploadFileName);

    int findbyteam(String t_name);

    void insertCap(int t_code, int u_code);

    List<team> selectTeamInUsers(String u_code);

    //팀코드로 팀 젇보,팀장 불러오기
    List<team> teamInfoByTeamCode(int t_code);

    //게임코드로 게임작성자(팀장) 찾기
    String writerByGameCode(int g_dung);

    String getteamname(int teamcode);

    void accessteam(int ucode, int tcode);
    //신청팀코드로 팀명
    String searchTeamNameByCode(int t_code);
    int getTeamCodeByName (String t_name);
    //로그인한 유저의 코드로 본인이 팀장인 팀코드,팀이름 가져오기
    List<team> getTeamByUserCode(String u_code);

}
