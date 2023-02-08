package footmap.footmap_spring.dao.matchDao;

import footmap.footmap_spring.dto.matchDto.match;
import footmap.footmap_spring.dto.teamDto.team;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MatchMapper {

    List<match> match(int u_code);

    List<team> getTeamdata(String t_name);

    List<match> getMyTeam(String u_code);

    List<match> getTeamwon(int t_code);

    List<match> getRealTeamwon(int u_codes);

    int getTeamcode(String t_name);

    void deleteteamwon(String u_code, String t_code);

    void insertteamwon(int u_code, int t_code);

    int usercodecheck(int u_code, int t_code) throws Exception;

    int tnamecheck(String t_name);

    List<match> SearchTeam(String t_name);

    int getcaptaincode(int teamcode);

    void deleteteam(int t_code);

    void deleteteammanagement(int t_code);

    void modifyteamname(String variable,String content);
}
