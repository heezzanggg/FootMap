package footmap.footmap_spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import footmap.footmap_spring.dto.matchDto.match;
import footmap.footmap_spring.dto.teamDto.team;
import footmap.footmap_spring.dto.userDto.User;
import footmap.footmap_spring.service.matchService.MatchService;
import footmap.footmap_spring.service.teamService.TeamService;
import footmap.footmap_spring.service.userService.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Team")
@Log4j2
public class MatchController {

    @Autowired
    private MatchService matchService;
    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    @RequestMapping("/t_search")
    public String t_search( Model model, Authentication authentication){
        if(authentication == null || authentication.getPrincipal() == null) {
            List<team> matchList = teamService.getTeamList();
            model.addAttribute("team",matchList);
            return "Team/t_search";
        }else {
            User users = (User) authentication.getPrincipal();
            int u_code = Integer.parseInt(users.getU_code());
            List<match> matchList = matchService.match(u_code);
            model.addAttribute("team", matchList);
            System.out.println(model);
            return "Team/t_search";
        }
    }
    @RequestMapping("/Teams")
    public String teams(String t_name, Model model){
        System.out.println("팀이름" + t_name);
        List<team> getTeamdata = matchService.getTeamdata(t_name);
        System.out.println("팀 데이터" + getTeamdata);
        int t_code = matchService.getTeamcode(t_name);
        System.out.println("팀코드드드" + t_code);
        ArrayList list = new ArrayList();
        List<match> getTeamwon = matchService.getTeamwon(t_code);
        for (int i=0; i<getTeamwon.toArray().length; i++) {
            int u_codes = getTeamwon.get(i).getU_code();
            List<User> getTeamUserList = userService.getTeamUserList(u_codes);
            list.add(getTeamUserList);
        }
        int c = list.toArray().length;
        int v = c-1;
        model.addAttribute("teamdata",getTeamdata);
        model.addAttribute("myteamwon",list);
        model.addAttribute("last" , v);
        return "Team/t_teams";
    }
    @GetMapping("/Myteamname")
    public String myteamname(Model model, String t_name,Authentication authentication){
//        User users = (User) authentication.getPrincipal();
//        List<match> getMyTeam = matchService.getMyTeam(users.getU_code());
        System.out.println("마이팀네임 " + t_name) ;
        int t_code = matchService.getTeamcode(t_name);
        ArrayList list = new ArrayList();
        List<match> getTeamwon = matchService.getTeamwon(t_code);
        System.out.println("겟팀원" + getTeamwon);
        for (int i=0; i<getTeamwon.toArray().length; i++) {
            int u_codes = getTeamwon.get(i).getU_code();
            List<User> getTeamUserList = userService.getTeamUserList(u_codes);
            System.out.println("user폼에서 받아온 팀원내역" + getTeamUserList);
            list.add(getTeamUserList);
        }
        int c = list.toArray().length;
        int v = c-1;
//        model.addAttribute("myteam",getMyTeam);
        model.addAttribute("myteamwon",list);
        model.addAttribute("t_code",t_code);
        model.addAttribute("t_name",t_name);
        model.addAttribute("last" , v);
        System.out.println(model);
        return "Team/teamwon";
    }

    @RequestMapping("/MyTeam")
    public String myteam(Authentication authentication, Model model,team Team){
        User users = (User) authentication.getPrincipal();
        List<match> getMyTeam = matchService.getMyTeam(users.getU_code());
        if (getMyTeam.size() == 0){
            return "Team/no_team";
        }else {
            System.out.println("겟마이팀네임이 나와야함" + getMyTeam);
            String t_name = getMyTeam.get(0).getT_name();
            System.out.println("티네임입니다." + t_name);
            int u_code = Integer.parseInt(users.getU_code());
            int t_code = getMyTeam.get(0).getT_code();
            ArrayList list = new ArrayList();
            System.out.println("현재 로그인된 유저의 유코드" + u_code);
            System.out.println("현재 로그인된 유저가 속한 팀의 팀코드" + t_code);
            List<match> getTeamwon = matchService.getTeamwon(t_code);
            System.out.println("겟팀원" + getTeamwon);
            for (int i = 0; i < getTeamwon.toArray().length; i++) {
                System.out.println("에이입니다" + getTeamwon.get(i).getU_code());
                int u_codes = getTeamwon.get(i).getU_code();
                System.out.println("현재 속한팀의 팀원들의 유코드" + u_codes);
                List<User> getTeamUserList = userService.getTeamUserList(u_codes);
                System.out.println("user폼에서 받아온 팀원내역" + getTeamUserList);
                list.add(getTeamUserList);

            }
            ;
            System.out.println("티코드" + t_code);
            int c = list.toArray().length;
            int v = c - 1;
            model.addAttribute("myteam", getMyTeam);
            model.addAttribute("myteamwon", list);
            model.addAttribute("t_code", t_code);
            model.addAttribute("t_name", t_name);
            model.addAttribute("last", v);
            System.out.println("브이입니다," + v);
            System.out.println(model);
            return "Team/my_team";
        }
    }
    @PostMapping("/Deleteteamwon")
    public String deleteteamwon(String u_code, String t_code) {
        System.out.println("유코드" + u_code);
        System.out.println("티코드" + t_code);
        matchService.deleteteamwon(u_code,t_code);
        return "redirect:/Team/MyTeam";
    }
    @RequestMapping("/insertTeam")
    public String insertTeam(int u_code, int t_code){
        System.out.println("insertuser" + u_code);
        System.out.println("insertteam" + t_code);
        matchService.insertteamwon(u_code,t_code);
        return "redirect:/Team/MyTeam";
    }





    @RequestMapping("/CreateTeam")
    public String createteam(Model model, Authentication authentication){
        User users = (User) authentication.getPrincipal();
        model.addAttribute("users", users);

        return "Team/createteam";
    }
//    @RequestMapping("/InsertTeam")
//    public String insertteam(String t_name, String t_intro,String t_stadium,int u_code,team Team){
//        teamService.createTeam(t_name,t_intro,t_stadium);
//        int t_code = teamService.findbyteam(t_name);
//        teamService.insertCap(t_code,u_code);
//        return "redirect:/Team/t_search";
//    }

    @ResponseBody
    @GetMapping("codecheck")
    public int codecheck(int u_code, int t_code)throws Exception{
        int result = matchService.usercodecheck(u_code,t_code);
        return result;
    }
    @ResponseBody
    @GetMapping("tnamecheck")
    public int tnamecheck2(String message){
        int result = matchService.tnamecheck(message);
        return result;
    }

    @ResponseBody
    @GetMapping("Tnamecheck")
    public int tnamecheck(String t_name){
        int result = matchService.tnamecheck(t_name);
        return result;
    }


    @RequestMapping("/SearchTeamname")
    @ResponseBody
    public String searchteamname (String t_name) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        List<match> SearchTeam = matchService.SearchTeam(t_name);
        System.out.println("서치 팀 입니다." + SearchTeam);
        hashMap.put("team",SearchTeam);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hashMap);
        return json;
    }

    @PostMapping("/deleteteam")
    @ResponseBody
    public void Deleteteam (String variable){
        System.out.println(variable);
        int t_code = matchService.getTeamcode(variable);
        matchService.deleteteammanagement(t_code);
        matchService.deleteteam(t_code);
    }

    @PostMapping("/modifyteamname")
    @ResponseBody
    public void modifyteamname (String variable, String content){
        System.out.println(variable);
        matchService.modifyteamname(variable,content);
    }


}
