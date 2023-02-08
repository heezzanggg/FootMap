package footmap.footmap_spring.service.JJokjiService;

import footmap.footmap_spring.dao.JJokjiDao.JJokjiMapper;
import footmap.footmap_spring.dao.matchDao.MatchMapper;
import footmap.footmap_spring.dto.JJokjiDao.JJokji;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JJokjiServiceImpl implements JJokjiService{
    private final JJokjiMapper jjokjiMapper;

    @Override
    public List<JJokji> getJJokji(int u_code) {
        List<JJokji> getJJokji = jjokjiMapper.getJJokji(u_code);
        return getJJokji;
    }

    @Override
    public void JJokjiin(int captiaincode, int u_code, String content,int teamcode) {

        jjokjiMapper.JJokjiin(captiaincode,u_code,content,teamcode);
    }

    @Override
    public void jjokcheck(int jcode) {
        jjokjiMapper.jjokcheck(jcode);
    }


    @Override
    public void requestGame(int j_sendName, int j_backName, String j_cont, int j_sendTeamCode, int j_backTeamCode, int g_code) {
        jjokjiMapper.requestGame(j_sendName,j_backName,j_cont,j_sendTeamCode,j_backTeamCode,g_code);
    }

    @Override
    public List<JJokji> getgameJJokji(int u_code) {
        List<JJokji> getgameJJokji = jjokjiMapper.getgameJJokji(u_code);
        return getgameJJokji;
    }

    @Override
    public List<JJokji> getJJokjiList(int u_code) {
        List<JJokji> getJJokjiList = jjokjiMapper.getJJokjiList(u_code);
        return getJJokjiList;
    }

    @Override
    public void deleteJJok(int g_code) {
        jjokjiMapper.deleteJJok(g_code);

    }
    @Override
    public List<JJokji>  getApplyGameJjokjiListByGameCode(int g_code) {
        List<JJokji>  getApplyGameJjokjiListByGameCode = jjokjiMapper.getApplyGameJjokjiListByGameCode(g_code);
        return getApplyGameJjokjiListByGameCode;
    }
}
