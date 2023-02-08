package footmap.footmap_spring.service.noticeService;

import footmap.footmap_spring.dto.PageRequestDTO;
import footmap.footmap_spring.dto.PageResponseDTO;
import footmap.footmap_spring.dto.noticeDto.notice;

import java.util.List;

public interface NoticeService {


    List<notice> finfo4(int f_CODE);

    List<notice> selectFinfo();


    PageResponseDTO<notice> noticeL(PageRequestDTO pageRequestDTO);

    PageResponseDTO<notice> finfo3(PageRequestDTO pageRequestDTO);

    List<notice> getCountL(Integer idx);


    notice getNoticeView(Integer idx);

    notice noticeL();

}
