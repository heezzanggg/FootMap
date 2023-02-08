package footmap.footmap_spring.dao.gameDao;

import footmap.footmap_spring.dto.PageRequestDTO;
import footmap.footmap_spring.dto.gameDto.Game;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GameMapper {
    //게임찾기1
    List<Game> getGameList();

    void addGame(Game game);

    List<Game> getGameByDung(Integer t_code);

    List<Game> getGameBySearch(Integer t_code);

    //게임찾기2
    List<Game> selectList(PageRequestDTO pageRequestDTO);

    //게임전체갯수
    int getCount(PageRequestDTO pageRequestDTO);

    List<Game> getFnameListByArea(String f_area);

    //게임 상세보기
    Game getViewGame(int g_code);

    //게임수정
    void writeUpdate(Game game);

    //게임삭제
    void deleteGame(int g_code);

    void gamecheck(int g_code,int t_code);
}