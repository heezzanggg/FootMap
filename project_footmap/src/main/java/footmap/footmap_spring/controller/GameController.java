package footmap.footmap_spring.controller;

import footmap.footmap_spring.dto.JJokjiDao.JJokji;
import footmap.footmap_spring.dto.PageRequestDTO;
import footmap.footmap_spring.dto.PageResponseDTO;
import footmap.footmap_spring.dto.gameDto.Game;
import footmap.footmap_spring.dto.teamDto.team;
import footmap.footmap_spring.dto.userDto.User;
import footmap.footmap_spring.service.JJokjiService.JJokjiService;
import footmap.footmap_spring.service.gameService.GameService;
import footmap.footmap_spring.service.noticeService.NoticeService;
import footmap.footmap_spring.service.teamService.TeamService;
import footmap.footmap_spring.service.userService.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/game")
@Log4j2
public class GameController {

    @Autowired
    private GameService gameService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private UserService userService;
    @Autowired
    private JJokjiService jJokjiService;

    //게임찾기(게임 메인 페이지)
//    @GetMapping("/search")
//    public String gameSearch(Model model){
//        log.info("================게임찾기페이지==================");
//        //등록된 게임 내역 가져오기
//        List<game> gameList = gameService.getGameList();
//
//        model.addAttribute("gameList",gameList);
//        log.info(model);
//        return "match_game/search_game";
//    }

    //게임찾기 2
    @GetMapping("/search")
    public String gameSearch(PageRequestDTO pageRequestDTO, Model model, BindingResult bindingResult){
        log.info("=========list==========");

        PageResponseDTO<Game> responseDTO = gameService.selectList(pageRequestDTO);
        log.info(responseDTO);
        log.info(pageRequestDTO);//PageRequestDTO(page=1, size=10, types=null, keyword=null, link=page=1&size=10)

        if(bindingResult.hasErrors()){
            pageRequestDTO = PageRequestDTO.builder().build();
        }

        //신청하기 버튼기능 : 로그인 유저(발신자) & 게시글 작성자(수신자)
        // User sender = (User) authentication.getPrincipal();// --> 로그인시만 가나ㅡㅇ... ㅠㅠ
        //User receiver = userService.getUserByGame(responseDTO.getDtoList().g);

        model.addAttribute("responseDTO",responseDTO);
        //model.addAttribute("sender",sender);
        System.out.println(model);
        return "match_game/search_game";

    }

    //게임등록페이지
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
//로그인없이 페이지 접속가능하지만 필요한페이지에 어노테이션 추가
    //@PreAutohorize(접근제한 표현식)뒤에 들어가는 문자열은 표현식 따라서 상황에맞게 작성해야함
    //현재쓰는 (hasRole(표현식)은 특정한 권한이 있는 사용자 를 허용시켜줌 ex)우리페이지 게시판
    @GetMapping("/registerForm")
    public String gameRegForm(Authentication authentication,Model model){
        log.info("게임등록페이지");

        //내일날짜 (게임일시 선택 사용)
        LocalDate tomorrows = LocalDate.now().plusDays(1);

        //로그인 정보 이용하여 팀장인 팀만 불러오기
        User users= (User) authentication.getPrincipal();
        List<team> chkCap = teamService.chkCap(users.getU_code());

        model.addAttribute("teams",chkCap);
        model.addAttribute("tomorrows",tomorrows);

        return "match_game/reg_game";
    }

    //게임등록
    @PostMapping("/register")
    public String gameAdd(Game game,Model model) {

        gameService.gameAdd( game );//유효성 검사 통과하면 데이터 insert

        return "redirect:/game/search";
    }

    //게임 상세보기
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/view")
    public String gameView(@RequestParam("g_code") Integer g_code, Model model,PageRequestDTO pageRequestDTO,
                           Authentication authentication) throws ParseException {
        log.info("======게임상세보기======");
        //등록된 게임 내역 가져오기
        Game game = gameService.getViewGame(g_code);

        log.info(game.getG_dung()); //2
        String writer = teamService.writerByGameCode(game.getG_dung());

        //날짜&시간 String to localdate
        String dateStr=game.getG_date();
        String timeStr=game.getG_time();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");

        Date date = formatter.parse(dateStr); // Thu Jan 26 00:00:00 KST 2023
        Date time = formatter2.parse(timeStr); //Thu Jan 01 16:40:00 KST 1970

        //로그인한 유저가 게임신청시, 본인이 팀장인 팀 정보(모달에 넣을 값)
        User users= (User) authentication.getPrincipal();
        List<team> searchTeam = teamService.getTeamByUserCode(users.getU_code());

        model.addAttribute("gameList",game);
        model.addAttribute("g_date",date);
        model.addAttribute("g_time",time);
        model.addAttribute("writer",writer);
        model.addAttribute("searchTeam",searchTeam);

        log.info(model);
        return "match_game/view_game";
    }

    //게임 수정하기 폼
    @RequestMapping("/updateForm")
    public String updateFrom(@RequestParam("g_code")Integer g_code, PageRequestDTO pageRequestDTO,Model model){

        Game game = gameService.getViewGame(g_code);

        //내일날짜 (게임일시 선택 사용)
        LocalDate tomorrows = LocalDate.now().plusDays(1);

        model.addAttribute("gameList",game);
        model.addAttribute("tomorrows",tomorrows);
        return "/match_game/update_game";
    }

    //게임 수정하기
    @PostMapping("/update")
    public String update(Game game, PageRequestDTO pageRequestDTO,
                         RedirectAttributes redirectAttributes,BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("has error");
            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
            redirectAttributes.addAttribute("g_code",game.getG_code());
            return "redirect:/game/updateForm?"+link;
        }

        gameService.writeUpdate(game);
        redirectAttributes.addFlashAttribute("result","modified");
        redirectAttributes.addAttribute("g_code",game.getG_code());
        //redirectAttributes.addAttribute("page",pageRequestDTO.getPage());
        //redirectAttributes.addAttribute("size",pageRequestDTO.getSize());
        log.info(redirectAttributes);

        return "redirect:/game/view";
    }

    //게임삭제
    @GetMapping("/delete")
    public String deleteGame(int g_code){
        jJokjiService.deleteJJok(g_code);
        log.info("삭제시작");
        gameService.deleteGame(g_code);
        return "redirect:/game/search";
    }

    //내 게임내역
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/list")
    public String gameList(Authentication authentication,Model model){
        log.info("=====================내게임내역 시작");
        //로그인한 유저 정보
        User users= (User) authentication.getPrincipal();

        //팀 정보 가져오기
        List<team> selectTeamInUsers = teamService.selectTeamInUsers(users.getU_code());

        model.addAttribute("teams",selectTeamInUsers);

        return "match_game/list_game";
    }

    @RequestMapping(value="/get_fNameOption")
    @ResponseBody //자바객체를 HTTP응답 본문의 객체로 변환
    public Map<Integer, String> getFNameOption(@RequestBody String optionValue1)
            throws IOException{
        List<Game> optionValue2 = gameService.getFnameListByArea(optionValue1);
        Map<Integer,String> optionList = new HashMap<>(); //f_code,f_name 넣을 optionList

        for(int i =0;i< optionValue2.size();i++){
            optionList.put(optionValue2.get(i).getF_code(),optionValue2.get(i).getF_name());
        }

        return optionList;
    }

    //나의 게임내역 ajax
    @RequestMapping(value = "/get_gameOption")
    @ResponseBody
    public List getGameOption(@RequestBody int optionValue1) {

        System.out.println("optionValue1(팀코드):"+optionValue1); //팀코드

        List<team> optionValue2 = teamService.teamInfoByTeamCode(optionValue1); //팀 정보
        List<Game> optionValue3 = gameService.getGameByDung(optionValue1); //우리팀이 등록한 게임 정보
        List<Game> optionValue4 = gameService.getGameBySearch(optionValue1); //우리팀이 신청한 게임 정보

        System.out.println("optionValue2(팀정보):" + optionValue2);
        System.out.println("optionValue3(우리팀이 등록한 게임 정보):" + optionValue3);
        System.out.println("optionValue4(우리팀이 신청한 게임 정보):" + optionValue4);

        List arrOpponentTeamNameOfRegGame = new ArrayList(); //변수(등록게임의 상대팀코드->팀명)(매칭완료)
        List arrOpponentTeamNameOfApplyGame = new ArrayList(); //변수(신청게임의 상대팀코드->팀명)

        //등록게임의 상대팀명을 변수에 담는 반복문
        for (int i = 0; i < optionValue3.size(); i++) {
            int OpponentTeamCodeOfRegGame = optionValue3.get(i).getG_search(); //등록게임의 상대팀코드
            arrOpponentTeamNameOfRegGame.add(teamService.searchTeamNameByCode(OpponentTeamCodeOfRegGame));
        }

        //신청게임의 상대팀명을 변수에 담는 반복문
        for (int i = 0; i < optionValue4.size(); i++) {
            int OpponentTeamCodeOfApplyGame = optionValue4.get(i).getG_dung();
            arrOpponentTeamNameOfApplyGame.add(teamService.searchTeamNameByCode(OpponentTeamCodeOfApplyGame));
        }

        System.out.println("등록게임의 상대팀명:"+ arrOpponentTeamNameOfRegGame);
        System.out.println("신청게임의 상대팀명:"+ arrOpponentTeamNameOfApplyGame);

        //등록게임코드 담는 반복문
        List dungGameCode = new ArrayList();
        for (int i = 0; i < optionValue3.size(); i++) {
            dungGameCode.add(optionValue3.get(i).getG_code());
        }
        System.out.println("dungGameCode(등록한 게임의 게임코드):" + dungGameCode);

        //게임코드로 등록한 게임에 신청쪽지 보낸 내용,팀코드 가져오기(매칭전!등록게임에 게임신청쪽지보낸  팀 유무 확인 용)
        List<List<JJokji>> getApplyGameJjokjiList = new ArrayList();
        System.out.println("등록한 게임 갯수:"+dungGameCode.size());

        //게임을 신청한 팀코드->이름 담을 변수(매칭전, 신청만 한 팀)
        List arrApplyTeamName = new ArrayList();

        //등록게임코드로 신청내역가져오기
        for (int i = 0; i < dungGameCode.size(); i++) {
            int num1 = (int) dungGameCode.get(i);
            System.out.println("num:" + num1);

            List<JJokji> jjokList = jJokjiService.getApplyGameJjokjiListByGameCode(num1);
            System.out.println("jjokList.size():"+jjokList.size());
            System.out.println("신청내역 X:"+jjokList.isEmpty()); //신청내역 X : true / 신청내역 O : false
            System.out.println(jjokList);

            //신청내역 O : jjokList.isEmpty() == false
            //getApplyGameJjokjiList 담고, 팀코드를 팀명으로 바꾸기
            if(jjokList.isEmpty() == false){
                //쪽지내용 담고
                getApplyGameJjokjiList.add(jJokjiService.getApplyGameJjokjiListByGameCode(num1));
                //팀코드를 팀명으로 바꾸기
                System.out.println("dsfgsdfgsdfg" + getApplyGameJjokjiList.get(i));
                int num2 = (int) getApplyGameJjokjiList.get(i).get(0).getJ_sendteam(); //등록게임에 신청한 팀 코드
                arrApplyTeamName.add(teamService.searchTeamNameByCode(num2));

//                //팀코드를 팀명으로 바꾸기
//                for (int j = 0; j < getApplyGameJjokjiList.size(); j++) {
//                    System.out.println("dsfgsdfgsdfg" + getApplyGameJjokjiList.get(j));
//                    int num2 = (int) getApplyGameJjokjiList.get(j).get(0).getJ_sendteam();
//
//                    arrApplyTeamName.add(teamService.searchTeamNameByCode(num2));
//                }
            }else{
                //신청내역X : jjokList.isEmpty() == true
                break;
            }
        }

//        System.out.println(getGameListByGameCode.get(0));
//        System.out.println(getGameListByGameCode.get(1));
//        System.out.println(getGameListByGameCode.get(0).get(0).getJ_sendteam());
//        System.out.println(getGameListByGameCode.get(1).get(0).getJ_sendteam());
        System.out.println("등록게임에대해 신청쪽지(getApplyGameJjokjiList):"+getApplyGameJjokjiList);
        System.out.println(arrApplyTeamName);

        List result = new ArrayList();
        result.add(optionValue2); //팀정보
        result.add(optionValue3); //등록게임
        result.add(optionValue4); //신청게임
        result.add(arrOpponentTeamNameOfRegGame); //등록게임의 상대 팀 이름
        result.add(arrOpponentTeamNameOfApplyGame); //신청게임의 상대 팀 이름

        result.add(getApplyGameJjokjiList); //등록한 게임에 신청쪽지 보낸팀 정보(매칭전!)
        result.add(arrApplyTeamName);//등록한 게임에 신청한 팀 이름(매칭전!)

        System.out.println(result);
        return result;
    }

    //게임신청하기
    @RequestMapping(value="/requestGame")
    @ResponseBody
    public String requestGame(String backTeamName,String content,String writer,String searchTeamCode,String gcode,Authentication authentication){
        System.out.println("게임신청하기");
        System.out.println(backTeamName); //게임 등록팀 이름
        System.out.println(content); //쪽지내용
        System.out.println(writer);//게임 등록자 코드
        System.out.println(searchTeamCode); //게임 신청팀 코드
        int g_code = Integer.parseInt(gcode);

        //로그인한 유저 정보
        User users= (User) authentication.getPrincipal();
        //등록팀이름 t_name으로 등록팀코드 가져오기
        //int j_backName = teamService.getTeamCodeByName(backTeamName); //받는 사람 코드
        int sendUserCode = Integer.parseInt(users.getU_code()); //보내는 사람 코드
        int backUserCode = Integer.parseInt(writer); //받는사람 코드(게임등록자)
        String j_cont = content; //쪽지내용
        int sendTeamCode = Integer.parseInt(searchTeamCode);//게임신청팀 코드
        int backTeamCode = teamService.getTeamCodeByName(backTeamName);//게임 등록팀 코드

        jJokjiService.  requestGame(sendUserCode, backUserCode, j_cont, sendTeamCode, backTeamCode,g_code);

        return null;
    }





}
