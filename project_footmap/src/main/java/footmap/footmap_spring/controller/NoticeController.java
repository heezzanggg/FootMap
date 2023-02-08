package footmap.footmap_spring.controller;

import footmap.footmap_spring.dto.PageRequestDTO;
import footmap.footmap_spring.dto.PageResponseDTO;
import footmap.footmap_spring.dto.noticeDto.notice;

import footmap.footmap_spring.service.noticeService.NoticeService;

import lombok.extern.log4j.Log4j2;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/Page")
@Log4j2
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    //공지사항
    @GetMapping ("/Notice")
    public String notice(@Valid PageRequestDTO pageRequestDTO, Model model, BindingResult bindingResult) {

        PageResponseDTO<notice> responseDTO = noticeService.noticeL(pageRequestDTO);

        if (bindingResult.hasErrors()) {
            pageRequestDTO = PageRequestDTO.builder().build();
        }
        model.addAttribute("responseDTO", responseDTO);

        return "Page/Notice/notice";
    }
    //공지사항 보기
    @RequestMapping("/notice_view")
    public String NoticeView( @RequestParam("idx")Integer idx,
                              Model model,
                              PageRequestDTO pageRequestDTO){

        log.info("================555555");

        notice Notice = noticeService.getNoticeView( idx );

        log.info("55555"+ idx);
        noticeService.getCountL( idx );
        model.addAttribute("notice", Notice );


        log.info("4444======"+model);


        return "Page/Notice/notice_view";


    }
    //이벤트
    @RequestMapping("/Event")
    public String Event( Model model ){
        notice Notice = noticeService.noticeL();
        model.addAttribute("notice",Notice);
        log.info("************" + model);
        System.out.println("EVENT");
        return "Page/Notice/Event";
    }


    //FAQ
    @RequestMapping("/FAQ")
    public String FAQ(){
        System.out.println("FAQ");
        return "Page/Notice/FAQ";
    }

    @RequestMapping("/FINFO")
    public String FINFO(Model model,int f_CODE){
        System.out.println("f코드입니다." + f_CODE);
        List<notice> noticeList = noticeService.finfo4(f_CODE);
        model.addAttribute("Info", noticeList);
        System.out.println("풋살장 정보");
        log.info(model + "++++++++");
        return "Page/Notice/FINFO/FINFO";
    }
    //풋살장 리스트
    @GetMapping("/FLIST")
    public String FLIST(@Valid PageRequestDTO pageRequestDTO, Model model, BindingResult bindingResult){
        log.info("---------------풋살장--------리스트시작--------");
        PageResponseDTO<notice> responseDTO = noticeService.finfo3(pageRequestDTO);


        log.info(responseDTO);
        log.info(pageRequestDTO);

        if(bindingResult.hasErrors()){
            pageRequestDTO = PageRequestDTO.builder().build();
        }
        model.addAttribute("responseDTO",responseDTO);
        log.info(model);
        return "Page/Notice/FINFO/FLIST";
    }


    //사이트 소개
    @RequestMapping("/s_intro")
    public String S_intro(){
        System.out.println("사이트 소개");
        return "Page/S_intro";

    }
    //사이트 준수 사항
    @RequestMapping("/s_rule")
    public String S_rule(){
        System.out.println("사이트 준수사항");
        return "Page/S_rule";

    }

}
