package footmap.footmap_spring.dao.JJokjiDao;

import footmap.footmap_spring.dto.JJokjiDao.JJokji;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface JJokjiMapper {
    void JJokjiin(int captiaincode, int u_code, String content,int teamcode);

    List<JJokji> getJJokji(int u_code);

    void jjokcheck(int jcode);

    void requestGame(int j_sendName, int j_backName, String j_cont, int j_sendTeamCode,int j_backTeamCode,int g_code );

    List<JJokji> getgameJJokji(int u_code);

    List<JJokji> getJJokjiList(int u_code);

    void deleteJJok(int g_code);

    List<JJokji> getApplyGameJjokjiListByGameCode(int g_code);

}
