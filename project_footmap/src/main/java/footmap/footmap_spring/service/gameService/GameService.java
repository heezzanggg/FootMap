package footmap.footmap_spring.service.gameService;

import footmap.footmap_spring.dto.PageRequestDTO;
import footmap.footmap_spring.dto.PageResponseDTO;
import footmap.footmap_spring.dto.gameDto.Game;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;

public interface GameService {

    //게임찾기 1
    List<Game> getGameList();

    //게임찾기2
    PageResponseDTO<Game> selectList(PageRequestDTO pageRequestDTO);
    void gameAdd(Game game);

    List<Game> getGameByDung(Integer t_code);

    List<Game> getGameBySearch(Integer t_code);

    List<Game> getFnameListByArea(String f_area);

    Game getViewGame(int g_code);

    void writeUpdate(Game game);

    void deleteGame(int g_code);

    void gamecheck(int g_code,int t_code);

//    Map<String,String> validateHandling(Errors errors);

}
