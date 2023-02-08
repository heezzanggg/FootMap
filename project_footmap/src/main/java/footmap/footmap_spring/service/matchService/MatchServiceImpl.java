package footmap.footmap_spring.service.matchService;

import footmap.footmap_spring.dao.matchDao.MatchMapper;
import footmap.footmap_spring.dto.matchDto.match;
import footmap.footmap_spring.dto.teamDto.team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor

public class MatchServiceImpl implements MatchService{

    private final MatchMapper matchMapper;
    @Override
    public List<match> match(int u_code) {
        return matchMapper.match(u_code);
    }

    @Override
    public List<team> getTeamdata(String t_name) {
        List<team> getTeamdata = matchMapper.getTeamdata(t_name);
        return getTeamdata;
    }

    @Override
    public List<match> getMyTeam(String u_code) {
        List<match> getMyTeam = matchMapper.getMyTeam(u_code);
        System.out.println("겟마이팀" + getMyTeam);
        return getMyTeam;
    }

    @Override
    public List<match> getTeamwon(int t_code) {
        List<match> getTeamwon = matchMapper.getTeamwon(t_code);
        System.out.println("매치서비스 겟 유저팀원" + getTeamwon);

        return getTeamwon;
    }

    @Override
    public List<match> getRealTeamwon(int u_codes) {
        List<match> getRealTeamwon = matchMapper.getRealTeamwon(u_codes);
        return getRealTeamwon;
    }

    @Override
    public int getTeamcode(String t_name) {
        int t_code = matchMapper.getTeamcode(t_name);
        return t_code;
    }

    @Override
    public void deleteteamwon(String u_code, String t_code) {
        System.out.println("매치서비스임플 딜리트팀원");
        matchMapper.deleteteamwon(u_code,t_code);
    }

    @Override
    public void insertteamwon(int u_code, int t_code) {
        matchMapper.insertteamwon(u_code,t_code);
    }

    @Override
    public int usercodecheck(int u_code, int t_code) throws Exception{
        try{
            int result = matchMapper.usercodecheck(u_code,t_code);
            return result;
        }catch (Exception e){
            System.out.println("유저코드중복확인 오류" + e.getMessage());
            throw e;
        }
    }

    @Override
    public int tnamecheck(String t_name) {
        int result = matchMapper.tnamecheck(t_name);
        return result;
    }

    @Override
    public List<match> SearchTeam(String t_name) {
        List<match> SearchTeam = matchMapper.SearchTeam(t_name);
        System.out.println("서비스임플 서치팀" + SearchTeam);
        return SearchTeam;
    }

    @Override
    public int getcaptaincode(int teamcode) {
        int getcaptaincode = matchMapper.getcaptaincode(teamcode);
        return getcaptaincode;
    }

    @Override
    public void deleteteam(int t_code) {
        matchMapper.deleteteam(t_code);

    }

    @Override
    public void deleteteammanagement(int t_code) {
        matchMapper.deleteteammanagement(t_code);
    }

    @Override
    public void modifyteamname(String variable, String content) {
        matchMapper.modifyteamname(variable,content);

    }


}
