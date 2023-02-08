package footmap.footmap_spring.controller;

import footmap.footmap_spring.dto.PageRequestDTO;
import footmap.footmap_spring.dto.PageResponseDTO;
import footmap.footmap_spring.dto.gameDto.Game;
import footmap.footmap_spring.dto.userDto.User;
import footmap.footmap_spring.service.gameService.GameService;
import footmap.footmap_spring.service.teamService.TeamService;
import footmap.footmap_spring.service.userService.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
@Log4j2
public class AdminController {

    @Autowired
    private GameService gameService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String adminList(){
        return "/admin/admin";
    }

    //게임리스트 불러오기
    @GetMapping("/gameList")
    public String getGameList(PageRequestDTO pageRequestDTO, Model model, BindingResult bindingResult){

        PageResponseDTO<Game> responseDTO = gameService.selectList(pageRequestDTO);
        log.info(responseDTO);
        log.info(pageRequestDTO);//PageRequestDTO(page=1, size=10, types=null, keyword=null, link=page=1&size=10)

        if(bindingResult.hasErrors()){
            pageRequestDTO = PageRequestDTO.builder().build();
        }

        model.addAttribute("responseDTO",responseDTO);
        System.out.println(model);
        return "/admin/gameList";
    }

    //게임상세보기
    @GetMapping("/gameView")
    public String gameView(@RequestParam("g_code") Integer g_code, Model model, PageRequestDTO pageRequestDTO,
                           Authentication authentication) throws ParseException {
        log.info("======게임상세보기======");
        //등록된 게임 내역 가져오기
        Game game = gameService.getViewGame(g_code);

        log.info(game.getG_dung()); //2
        //게임등록한 유저코드(팀장)
        String writer = teamService.writerByGameCode(game.getG_dung());

        //날짜&시간 String to localdate
        String dateStr=game.getG_date();
        String timeStr=game.getG_time();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");

        Date date = formatter.parse(dateStr); // Thu Jan 26 00:00:00 KST 2023
        Date time = formatter2.parse(timeStr); //Thu Jan 01 16:40:00 KST 1970

        //신청팀 코드로 팀명 가져오기
        String searchTeamName = teamService.searchTeamNameByCode(game.getG_search());

        //팀 코드로 팀원들 목록&정보 가져오기
        List<User> dungTeamMember = userService.getTeamMemberByTeamCode(game.getG_dung()); //게임등록팀 멤버
        List<User> searchTeamMember = userService.getTeamMemberByTeamCode(game.getG_search()); //게임등록팀 멤버

//[User(u_code=1, u_id=null, u_pw=null, u_name=null, u_nick=test1, u_birth=null, u_tel=null, u_mail=null, u_assi=0, u_goal=0, u_cut=0, u_sex=0, user_auth=null)]
//[User(u_code=2, u_id=null, u_pw=null, u_name=null, u_nick=test2, u_birth=null, u_tel=null, u_mail=null, u_assi=0, u_goal=0, u_cut=0, u_sex=0, user_auth=null)]
        model.addAttribute("gameList",game);
        model.addAttribute("g_date",date);
        model.addAttribute("g_time",time);
        model.addAttribute("writer",writer);
        model.addAttribute("searchTeamName",searchTeamName);
        model.addAttribute("dungTeamMember",dungTeamMember);
        model.addAttribute("searchTeamMember",searchTeamMember);

        log.info(model);

        //{pageRequestDTO=PageRequestDTO(page=1, size=10, types=null, keyword=, link=page=1&size=10&keyword=),
        // org.springframework.validation.BindingResult.pageRequestDTO=org.springframework.validation.BeanPropertyBindingResult: 0 errors,
        // gameList=Game(g_code=1, g_dung=4, g_search=0, g_date=2023-02-03, g_time=23:30:00, g_peo=3vs3, g_magam=2, g_intro=ddd, f_name=부산 준타스 풋살 아레나 화이트, f_area=부산, t_name=team4, f_code=51003),
        // g_date=Fri Feb 03 00:00:00 KST 2023,
        // g_time=Thu Jan 01 23:30:00 KST 1970,
        // writer=1,
        // searchTeam=[]}
        return "/admin/gameView";
    }

    //경기정보 & 유저 기록 수정폼
    @RequestMapping("/updateGameViewForm")
    public String updateFrom(@RequestParam("g_code")Integer g_code, PageRequestDTO pageRequestDTO,Model model) throws ParseException {

        Game game = gameService.getViewGame(g_code);
        System.out.println(game);
        //Game(g_code=2, g_dung=4, g_search=5, g_date=2023-02-01, g_time=23:30:00, g_peo=3vs3, g_magam=2, g_intro=ㅇ, f_name=대구 팔공 K 스타디움, f_area=대구/경북, t_name=team4, f_code=53003)



        //날짜&시간 String to localdate
        String dateStr=game.getG_date();
        String timeStr=game.getG_time();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");

        Date date = formatter.parse(dateStr); // Thu Jan 26 00:00:00 KST 2023
        Date time = formatter2.parse(timeStr); //Thu Jan 01 16:40:00 KST 1970


        //신청팀 코드로 팀명 가져오기
        String searchTeamName = teamService.searchTeamNameByCode(game.getG_search());

        //팀 코드로 팀원들 목록&정보 가져오기
        List<User> dungTeamMember = userService.getTeamMemberByTeamCode(game.getG_dung()); //게임등록팀 멤버
        List<User> searchTeamMember = userService.getTeamMemberByTeamCode(game.getG_search()); //게임등록팀 멤버


        model.addAttribute("gameList",game);
        model.addAttribute("g_date",date);
        model.addAttribute("g_time",time);
        model.addAttribute("searchTeamName",searchTeamName);
        model.addAttribute("dungTeamMember",dungTeamMember);
        model.addAttribute("searchTeamMember",searchTeamMember);

        return "/admin/updateGameView";
    }

    //경기정보 & 유저 기록 수정
    @PostMapping("/updateGameView")
    public String update(int g_code,@RequestParam List<Integer> u_code,@RequestParam List<Integer> u_assi,
                         @RequestParam List<Integer> u_goal,@RequestParam List<Integer> u_cut,
                         PageRequestDTO pageRequestDTO,
                         RedirectAttributes redirectAttributes, BindingResult bindingResult){
//        log.info("====================================");
//        log.info(users);
//        log.info("====================================");

        log.info(u_code.getClass().getName());
        log.info(u_code.get(1));
        log.info(u_code.get(0));

        for(int i=0;i<u_code.size();i++){

            int code=u_code.get(i);
            int assi=u_assi.get(i);
            int goal=u_goal.get(i);
            int cut =u_goal.get(i);

            userService.updateUserRecord(code,assi,goal,cut);

            code = 0;
            assi = 0 ;
            goal = 0;
            cut = 0;

        }


        System.out.println("관리자모드 개인 기록 수정 시작이디~!!!!");
//        if(bindingResult.hasErrors()){
//            log.info("has error");
//            String link = pageRequestDTO.getLink();
//            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
//            redirectAttributes.addAttribute("g_code",game.getG_code());
//
//            return "redirect:/admin/updateGameViewForm?"+link;
//        }
        redirectAttributes.addFlashAttribute("result","modified");
        redirectAttributes.addAttribute("g_code",g_code);
        redirectAttributes.addAttribute("page",pageRequestDTO.getPage());
        redirectAttributes.addAttribute("size",pageRequestDTO.getSize());
//        log.info(redirectAttributes);

        return "redirect:/admin/gameView";
        //return "redirect:/admin/gameList";
    }

    //게임삭제
    @GetMapping("/deleteGame")
    public String deleteGame(int g_code){
        log.info("삭제시작");
        gameService.deleteGame(g_code);
        return "redirect:/admin/gameList";
    }
}
