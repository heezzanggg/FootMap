package footmap.footmap_spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import footmap.footmap_spring.dto.JJokjiDao.JJokji;
import footmap.footmap_spring.dto.matchDto.match;
import footmap.footmap_spring.dto.teamDto.team;
import footmap.footmap_spring.dto.userDto.User;
import footmap.footmap_spring.service.JJokjiService.JJokjiService;
import footmap.footmap_spring.service.gameService.GameService;
import footmap.footmap_spring.service.matchService.MatchService;
import footmap.footmap_spring.service.teamService.TeamService;
import footmap.footmap_spring.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/ajax")
public class AjaxController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private JJokjiService jjokjiService;
    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Autowired
    private MatchService matchService;
    @GetMapping("/data")
    public String getData(Authentication authentication) throws JsonProcessingException {
        User users = (User) authentication.getPrincipal();
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("users",users);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hashMap);
        System.out.println("sdfsdjfshdfjsdfsdfbsdjfsbdfjsdfbsd");
        return json;
    }

    @PostMapping("/access")
    public void access(int ucode,int jcode, String tname){
        int tcode = teamService.findbyteam(tname);
        teamService.accessteam(ucode,tcode);
        jjokjiService.jjokcheck(jcode);
        System.out.println(tcode);
    }

    @PostMapping("/denied")
    public void denied(int ucode,int jcode, String tname){
        jjokjiService.jjokcheck(jcode);

    }

    @GetMapping("/newdata")
    public String newdata(int u_code) throws JsonProcessingException {
        ArrayList list = new ArrayList();
        ArrayList list2 = new ArrayList();
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        List<JJokji> getJJokji = jjokjiService.getJJokji(u_code);
        System.out.println("뉴데이터 겟족" + getJJokji);
        for(int i =0; i<getJJokji.size();i++){
            int jjoku_code = getJJokji.get(i).getJ_sendname();
            int teamcode = getJJokji.get(i).getJ_sendteam();
            String teamname = teamService.getteamname(teamcode);
            System.out.println("팀이름입니다" + teamname);
            String getnick = userService.getnick(jjoku_code);
            list.add(getnick);
            list2.add(teamname);
        }
        hashMap.put("JJokji",getJJokji);
        hashMap.put("jjoku_nick",list);
        hashMap.put("teamname",list2);
        System.out.println("뉴데이터 해쉬맵" + hashMap);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hashMap);

        return json;
    }

    @PostMapping("/Accept")
    public void accept(String t_name,String gcode,String jcode){
        System.out.println(t_name);
        System.out.println(gcode);
        System.out.println(jcode);
        int j_code = Integer.parseInt(jcode);
        int g_code = Integer.parseInt(gcode);
        int t_code = teamService.getTeamCodeByName(t_name);
        jjokjiService.jjokcheck(j_code);
        System.out.println("성공" + t_code);
        gameService.gamecheck(g_code,t_code);
        System.out.println("진짜 성공");


    }

    @PostMapping("/Denied")
    public void denide(String t_name, String gcode, String jcode){
        int j_code = Integer.parseInt(jcode);
        jjokjiService.jjokcheck(j_code);

    }

    @GetMapping("/CERTIFICATION")
    public String certification(String u_pw, User user, Authentication authentication, Model model) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        User users = (User) authentication.getPrincipal();
        boolean data =userService.certificationupdate(u_pw,users,authentication);
        System.out.println("에이젝스 데이터" + data);
        hashMap.put("data",data);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hashMap);
        return json;

    }

    @GetMapping("/Myteamname")
    public String myteamname(Model model, String t_name,Authentication authentication) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
//        User users = (User) authentication.getPrincipal();
//        List<match> getMyTeam = matchService.getMyTeam(users.getU_code());
        List<team> mathList = matchService.getTeamdata(t_name);
        System.out.println("리스트 팀팀팀" + mathList);
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
        System.out.println("이거입니까?" + c);
        hashMap.put("myteamwon",list);
        hashMap.put("t_code",t_code);
        hashMap.put("t_name",t_name);
        hashMap.put("last",c);
        hashMap.put("teamdata",mathList);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hashMap);

        return json;
    }


    @GetMapping("/TeamData")
    public int teamdata(Authentication authentication){
        User users = (User) authentication.getPrincipal();
        String u_code = users.getU_code();
        List<match> plzmat = matchService.getMyTeam(u_code);
        System.out.println(plzmat);
        int result = plzmat.size();

        return result;
    }

    @RequestMapping(value = "/uploadTest", method = RequestMethod.POST)
    public void  uploadTestPOST(MultipartFile[] uploadFile,String t_name,String t_intro,String t_stadium,int u_code) {
        System.out.println("업로드 파일 t_name" + t_name);
        System.out.println("업로드 파일 t_intro" + t_intro);
        System.out.println("업로드 파일 t_stadium" + t_stadium);
        System.out.println("업로드 파일 u_Code" + u_code);
        // 내가 업로드 파일을 저장할 경로
        String uploadFolder = "C:\\Users\\aktmx\\OneDrive\\바탕 화면\\restartF_B-JJuny\\src\\main\\resources\\static\\img";
        for (MultipartFile multipartFile : uploadFile) {
            String uploadFileName2 = multipartFile.getOriginalFilename();
            // 저장할 파일, 생성자로 경로와 이름을 지정해줌.
            File saveFile = new File(uploadFolder, uploadFileName2);
            String load = "/img/";
            String uploadFileName = load + uploadFileName2;
            System.out.println("업로드 파일네임 진짜!!" + uploadFileName);
            teamService.createTeam(t_name,t_intro,t_stadium,uploadFileName);
            int t_code = teamService.findbyteam(t_name);
            teamService.insertCap(t_code,u_code);
            System.out.println("성공!");


            try {
                // void transferTo(File dest) throws IOException 업로드한 파일 데이터를 지정한 파일에 저장
                multipartFile.transferTo(saveFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
