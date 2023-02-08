package footmap.footmap_spring.controller;

import footmap.footmap_spring.dto.JJokjiDao.JJokji;
import footmap.footmap_spring.dto.teamDto.team;
import footmap.footmap_spring.dto.userDto.User;
import footmap.footmap_spring.service.JJokjiService.JJokjiService;
import footmap.footmap_spring.service.teamService.TeamService;
import footmap.footmap_spring.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;

@Controller
@Log4j2
@RequiredArgsConstructor
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private JJokjiService jjokjiService;

    @RequestMapping("/")//메인페이지 userService의 getuserList와 getTopuserLIst로 선수전체의 목록과 top3선수의 목록을 가져옴
    public String home(Model model,Authentication authentication) {
        if(authentication == null || authentication.getPrincipal() == null){
            List<User> userList = userService.getUserList();
            List<User> getTopuserList = userService.getTopuserList();
            model.addAttribute("user", userList);
            model.addAttribute("Topuser", getTopuserList);
            List<team> teamList = teamService.getTeamList();
            List<team> getTopTeamList = teamService.getTopTeam();
            model.addAttribute("team", teamList);
            model.addAttribute("topteam", getTopTeamList);
            System.out.println("SDfsdfsdfsdf"+ getTopuserList.get(0));
            return "home/home";
        }else{
            User users = (User) authentication.getPrincipal();
            int u_code = Integer.parseInt(users.getU_code());
            ArrayList list = new ArrayList();
            ArrayList list2 = new ArrayList();
            List<JJokji> getJJokji = jjokjiService.getJJokji(u_code);
            System.out.println(getJJokji);
            for(int i =0; i<getJJokji.size();i++){
                int jjoku_code = getJJokji.get(i).getJ_sendname();
                int teamcode = getJJokji.get(i).getJ_sendteam();
                String teamname = teamService.getteamname(teamcode);
                System.out.println("팀이름입니다" + teamname);
                String getnick = userService.getnick(jjoku_code);
                list.add(getnick);
                list2.add(teamname);
            }
            System.out.println("리스트입니다" + list);
            List<User> userList = userService.getUserList();
            List<User> getTopuserList = userService.getTopuserList();
            model.addAttribute("user", userList);
            model.addAttribute("Topuser", getTopuserList);
            List<team> teamList = teamService.getTeamList();
            List<team> getTopTeamList = teamService.getTopTeam();
            model.addAttribute("team", teamList);
            model.addAttribute("topteam", getTopTeamList);
            model.addAttribute("JJokji",getJJokji);
            model.addAttribute("jjoku_nick",list);
            model.addAttribute("teamname",list2);
            System.out.println("Sdfsdfsdf0");
            return "home/home";
        }
    }



    @RequestMapping("/signUp")//회원가입페이지로 이동
    public String signup() {
        return "home/signup";
    }




    @PostMapping("/Write")//회원가입 폼
    public String saveUser(@ModelAttribute @Valid User user, Errors errors, Model model) {//UserMapper에서 오류문구 적용함
        if(errors.hasErrors()){
            /* 회원가입 실패시 입력 데이터 값을 유지 */
            model.addAttribute("user", user);

            /* 유효성 통과 못한 필드와 메시지를 핸들링 */
            Map<String, String> validatorResult = userService.validateHandling(errors);//에러메세지 받는곳
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "home/signup";//유효성검사를 통과하지못하면 다시 회원가입페이지로 이동하며 동시에 오류메세지 전달
        }
        userService.saveUser(user);//유효성검사를 통과하면 데이터 insert 후 로그인페이지로 redirect
        return "redirect:/login";
    }


    @GetMapping("/updateForm")
    public String updateForm(Authentication authentication, Model model) {

        User users= (User) authentication.getPrincipal();
        model.addAttribute("users", users);
        return "home/updateform";
    }

    @PostMapping("/userupdateForm")
    public String updateuser(User user,Authentication authentication) {
        User users = (User) authentication.getPrincipal();
        userService.UpdateUser(user);
        System.out.println("이전꺼" +  users.getU_pw());
        System.out.println("바뀐거" + user.getU_pw());
        return"redirect:/logout";
    }

//    @RequestMapping("/Certificationupdate")
//    public boolean  certificationupdateform(String u_pw,User user,Authentication authentication,Model model){
//        User users = (User) authentication.getPrincipal();
//        model.addAttribute("users",users);
//        model.addAttribute("cerification",userService.certificationupdate(u_pw,users,authentication));
//        userService.certificationupdate(u_pw,users,authentication);
//        System.out.println("여기까지오긴함~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        System.out.println("서비스 임플에서 받아온 값" + model);
//        System.out.println("체크" + userService.certificationupdate(u_pw,users,authentication));
//        boolean data =userService.certificationupdate(u_pw,users,authentication);
//        return data;
////        if ( userService.certificationupdate(u_pw,users,authentication)){
////            return "home/updateform";
////
////        }else {
////            return "/home/certificationupdateForm";
////        }
//    }
//    @RequestMapping("/CertificationupdateForm")
//    public String certificationupdateform(){
//        return "home/certificationupdateForm";
//    }




    @GetMapping("/login")//로그인페이지
    public String loginGET(@RequestParam(value = "error", required = false)String error,
                         @RequestParam(value = "exception", required = false)String exception,//@RequestParam로 json으로 넘어오는 오류문구를 받음
                         Model model){
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);//오류종류와 오류문구를 가지고 로그인 페이지로 이동
        System.out.println(model);
        return "/login";
    }
    @GetMapping("/Mypage")
    public String userAccess(Model model, Authentication authentication){
        User users = (User) authentication.getPrincipal();
        model.addAttribute("users", users);
        List<team> getTeaminUser = teamService.getTeaminUser(users.getU_code());
        model.addAttribute("userinteam", getTeaminUser);
        System.out.println(model);

        return "home/mypage";
    }


    @ResponseBody
    @GetMapping("Idchecklogic")
    public int Double(User user) throws Exception{
        int result = userService.DoubleCheck(user);
        System.out.println("여기에 결과값이 왓다" + result);
        return result;
    }
    @ResponseBody
    @GetMapping("MailcheckLogic")
    public int Mailcheck(User user) throws Exception{
        int result = userService.Mailcheck(user);
        return result;
    }
    @ResponseBody
    @GetMapping("mailcheck")
    public int chcheck(String u_mail, String u_name) throws Exception{
        int result = userService.emailcheck(u_mail,u_name);
        System.out.println("mailcheck 홈 컨트롤러 " + result);
        return result;
    }
    @RequestMapping("/Findid_password")
        public String findid_password(){
            return "home/findId";
    }

    @ResponseBody
    @GetMapping("FIndid")
    public String Find(User user) throws Exception{
        String id = userService.findid(user);
        System.out.println("findid 결과값" + id);

        return id;
    }


}
