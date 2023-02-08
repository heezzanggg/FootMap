package footmap.footmap_spring.service.JJokjiService;

import footmap.footmap_spring.dto.JJokjiDao.JJokji;

import java.util.List;

public interface JJokjiService {


    List<JJokji> getJJokji(int u_code);

    void JJokjiin(int captiaincode, int u_code, String content,int teamcode);


    void jjokcheck(int jcode);

    void requestGame(int j_sendName, int j_backName, String j_cont, int j_sendTeamCode,int j_backTeamCode ,int g_code);

    List<JJokji> getgameJJokji(int u_code);

    List<JJokji> getJJokjiList(int u_code);

    void deleteJJok(int g_code);

    List<JJokji>  getApplyGameJjokjiListByGameCode(int g_code);

}
