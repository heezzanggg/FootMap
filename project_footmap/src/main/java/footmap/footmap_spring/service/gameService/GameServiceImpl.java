package footmap.footmap_spring.service.gameService;

import footmap.footmap_spring.dao.gameDao.GameMapper;
import footmap.footmap_spring.dto.PageRequestDTO;
import footmap.footmap_spring.dto.PageResponseDTO;
import footmap.footmap_spring.dto.gameDto.Game;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class GameServiceImpl implements GameService{

    private final GameMapper gameMapper;

    //게임찾기1
    @Override
    public List<Game> getGameList() {
        return gameMapper.getGameList();
    }

    //게임찾기2
    @Override
    public PageResponseDTO<Game> selectList(PageRequestDTO pageRequestDTO) {

        List<Game> dtoList = gameMapper.selectList(pageRequestDTO);
        log.info(dtoList);

        int total = gameMapper.getCount(pageRequestDTO);

        PageResponseDTO<Game> pageResponseDTO = PageResponseDTO.<Game>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();

        return pageResponseDTO;
    }

    @Override
    public void gameAdd(Game game) {
        gameMapper.addGame(game);
    }

    @Override
    public List<Game> getGameByDung(Integer t_code) {
        List<Game> getGameByDung = gameMapper.getGameByDung(t_code);
        return getGameByDung;
    }

    @Override
    public List<Game> getGameBySearch(Integer t_code) {
        List<Game> getGameBySearch = gameMapper.getGameBySearch(t_code);
        return getGameBySearch;
    }

    @Override
    public List<Game> getFnameListByArea(String f_area) {
        List<Game> getFnameListByArea = gameMapper.getFnameListByArea(f_area);
        return getFnameListByArea;
    }

    @Override
    public Game getViewGame(int g_code) {
        Game game = gameMapper.getViewGame(g_code);
        return game;
    }

    @Override
    public void writeUpdate(Game game) {
        gameMapper.writeUpdate(game);
    }

    @Override
    public void deleteGame(int g_code) {
        gameMapper.deleteGame(g_code);
    }

    @Override
    public void gamecheck(int g_code,int t_code) {gameMapper.gamecheck(g_code,t_code);
    }

//    @Transactional
//    public Map<String, String> validateHandling(Errors errors) {
//        Map<String,String> validatorResult = new HashMap<>();
//
//        /*유효성 검사에 실패한 필드 목록 받음*/
//        for(FieldError error: errors.getFieldErrors()){
//            String validKeyName = String.format("valid_%S",error.getField());
//            validatorResult.put(validKeyName,error.getDefaultMessage());
//        }
//        return validatorResult;
//    }

}
