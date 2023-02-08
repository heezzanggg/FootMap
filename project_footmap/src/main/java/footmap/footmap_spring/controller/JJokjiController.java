package footmap.footmap_spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import footmap.footmap_spring.dto.JJokjiDao.JJokji;
import footmap.footmap_spring.dto.userDto.User;
import footmap.footmap_spring.service.JJokjiService.JJokjiService;
import footmap.footmap_spring.service.matchService.MatchService;
import footmap.footmap_spring.service.teamService.TeamService;
import footmap.footmap_spring.service.userService.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/JJokji")
@Log4j2
public class JJokjiController {
    @Autowired
    private MatchService matchService;
    @Autowired
    private JJokjiService jjokjiService;
    @Autowired
    private UserService userService;
    @Autowired
    private TeamService teamService;
    @PostMapping("/TeamAlerm")
    @ResponseBody
    public String  teamalerm(String t_name,String content,String nulldata,Authentication authentication) {
        User users = (User) authentication.getPrincipal();
        int u_code = Integer.parseInt(users.getU_code());
        System.out.println(u_code);
        int teamcode = matchService.getTeamcode(t_name);
        System.out.println("팀코드" + teamcode);//팀이름으로 가져온 팀 코드
        int captiaincode = matchService.getcaptaincode(teamcode);
        System.out.println("팀장코드" + captiaincode);//클릭한 팀의 팀장코드
        jjokjiService.JJokjiin(captiaincode,u_code,content,teamcode);
        return nulldata;
    }

    @GetMapping("/getJJok")
    @ResponseBody
    public String getJjok(int u_code)throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        List<JJokji> getJJokji = jjokjiService.getJJokjiList(u_code);
        hashMap.put("JJokjiList",getJJokji);
        int size = getJJokji.size();
        hashMap.put("JJokjiListSize",size);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hashMap);
        System.out.println("Sdfsdfsdfsdkfbsudfs!!!!!!!!!!!!!!!!!s");
        return json;
    }
    @GetMapping("/getgameJJokji")
    @ResponseBody
    public String getgamejjokji(int u_code)throws JsonProcessingException{
        ArrayList list = new ArrayList();
        ArrayList list2 = new ArrayList();
        ArrayList list3 = new ArrayList();
        ArrayList list4 = new ArrayList();
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        List<JJokji> getgameJJokji = jjokjiService.getgameJJokji(u_code);
        System.out.println("게임 쪽지" + getgameJJokji);
        for(int i=0; i<getgameJJokji.size();i++){
            int sendu_code = getgameJJokji.get(i).getJ_sendname();
            int backu_code = getgameJJokji.get(i).getJ_backname();
            int sendteamcode = getgameJJokji.get(i).getJ_sendteam();
            int backteamcode = getgameJJokji.get(i).getJ_backteam();
            String getsendnick = userService.getnick(sendu_code);
            String getbacknick = userService.getnick(backu_code);
            String sendteamname = teamService.getteamname(sendteamcode);
            String backteamname = teamService.getteamname(backteamcode);
            list.add(getsendnick);
            list2.add(getbacknick);
            list3.add(sendteamname);
            list4.add(backteamname);
        }
        hashMap.put("sendnick",list);
        hashMap.put("backnick",list2);
        hashMap.put("sendteamname",list3);
        hashMap.put("backteamname",list4);
        hashMap.put("gameJJokji",getgameJJokji);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hashMap);
        return json;
    }

    @RequestMapping("/Confirm")
    public String confirm(Authentication authentication, Model model){
        User users = (User) authentication.getPrincipal();
        int u_code = Integer.parseInt(users.getU_code());
        List<JJokji> getJJokji = jjokjiService.getJJokji(u_code);
        model.addAttribute("JJokji",getJJokji);
        System.out.println("쪽찌모델입니다" + model);
        return "/JJok/JJokjiList";

    }
}
