package footmap.footmap_spring.service.teamService;

import footmap.footmap_spring.dto.teamDto.team;

import java.util.List;

public interface TeamService {
    List<team> getTeamList();

    List<team> getTopTeam();

    List<team> getTeaminUser(String u_code);

    List<team> chkCap(String u_code);

    void createTeam(String t_name, String t_intro, String t_stadium, String uploadFileName);

    int findbyteam(String t_name);

    void insertCap(int t_code, int u_code);
    List<team> selectTeamInUsers(String u_code);

    List<team> teamInfoByTeamCode(int t_code);

    String writerByGameCode(int g_dung);

    String getteamname(int teamcode);

    void accessteam(int ucode, int tcode);
    String searchTeamNameByCode(int t_code);

    int getTeamCodeByName(String t_name);

    List <team> getTeamByUserCode(String u_code);
}
