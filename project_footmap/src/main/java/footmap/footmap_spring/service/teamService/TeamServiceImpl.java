package footmap.footmap_spring.service.teamService;

import footmap.footmap_spring.dao.teamDao.TeamMapper;
import footmap.footmap_spring.dto.teamDto.team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamMapper teamMapper;

    @Override
    public List<team> getTeamList() {

        return teamMapper.getTeamList();
    }

    @Override
    public List<team> getTopTeam() {
        return teamMapper.getTopTeam();
    }

    @Override
    public List<team> getTeaminUser(String u_code) {
        List<team> getTeaminUser = teamMapper.getTeaminUser(u_code);
        return getTeaminUser;
    }

    @Override
    public List<team> chkCap(String u_code) {
        List<team> chkCap = teamMapper.chkCap(u_code);
        return chkCap;
    }

    @Override
    public void createTeam(String t_name, String t_intro, String t_stadium, String uploadFileName) {
        System.out.println("팀서비스 임플 t_name" + t_name );
        teamMapper.createTeam(t_name,t_intro,t_stadium, uploadFileName);
        System.out.println("23123123123123123123");
    }

    @Override
    public int findbyteam(String t_name) {
       int findbyteam =  teamMapper.findbyteam(t_name);
        System.out.println("팀 서비스 팀 코드" + findbyteam);
        return findbyteam;
    }

    @Override
    public void insertCap(int t_code, int u_code) {
        System.out.println("뭐가문젠데");
        teamMapper.insertCap(t_code,u_code);
    }

    @Override
    public List<team> selectTeamInUsers(String u_code) {
        List<team> selectTeamInUsers = teamMapper.selectTeamInUsers(u_code);
        return selectTeamInUsers;
    }

    @Override
    public List<team> teamInfoByTeamCode(int t_code) {
        List<team> teamInfoByTeamCode = teamMapper.teamInfoByTeamCode(t_code);
        return teamInfoByTeamCode;
    }

    @Override
    public String writerByGameCode(int g_dung) {
        String writerByGameCode = teamMapper.writerByGameCode(g_dung);
        return writerByGameCode;
    }

    @Override
    public String getteamname(int teamcode) {
        String getteamname = teamMapper.getteamname(teamcode);
        return getteamname;
    }

    @Override
    public void accessteam(int ucode, int tcode) {
        teamMapper.accessteam(ucode,tcode);
    }
    @Override
    public String searchTeamNameByCode(int t_code) {
        String searchTeamNameByCode = teamMapper.searchTeamNameByCode(t_code);
        return searchTeamNameByCode;
    }
    @Override
    public int getTeamCodeByName(String t_name) {
        int getTeamCodeByName = teamMapper.getTeamCodeByName(t_name);
        return getTeamCodeByName;
    }

    @Override
    public List<team> getTeamByUserCode(String u_code) {
        List<team> getTeamByUserCode = teamMapper.getTeamByUserCode(u_code);
        return getTeamByUserCode;
    }
}
