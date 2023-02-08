package footmap.footmap_spring.service.noticeService;

import footmap.footmap_spring.dao.noticeDao.NoticeMapper;
import footmap.footmap_spring.dto.PageRequestDTO;
import footmap.footmap_spring.dto.PageResponseDTO;
import footmap.footmap_spring.dto.noticeDto.notice;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Log4j2
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;


    @Override
    public List<notice> finfo4(int f_CODE) {
        return noticeMapper.finfo4(f_CODE);
    }

    @Override
    public List<notice> selectFinfo() {
        return noticeMapper.selectFinfo();
    }

    @Override
    public PageResponseDTO<notice> noticeL(PageRequestDTO pageRequestDTO) {


        List<notice> dtoList = noticeMapper.noticeL(pageRequestDTO);
        log.info(dtoList);
        int total = noticeMapper.getCountL(pageRequestDTO);

        PageResponseDTO<notice> pageResponseDTO = PageResponseDTO.<notice>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();

        return pageResponseDTO;
    }

    @Override
    public PageResponseDTO<notice> finfo3(PageRequestDTO pageRequestDTO) {
        log.info("================서비스임플 finfo3===================");

        List<notice> dtoList = noticeMapper.finfo3(pageRequestDTO);
        log.info(dtoList);


        int total = noticeMapper.Count(pageRequestDTO);
        PageResponseDTO<notice> pageResponseDTO = PageResponseDTO.<notice>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        log.info(pageResponseDTO);
        return pageResponseDTO;
    }

    @Override
    public List<notice> getCountL(Integer idx)  {


        return null;
    }



    @Override
    public notice getNoticeView(Integer idx) {
        notice Notice = noticeMapper.getNoticeView(idx);

        return Notice;
    }

    @Override
    public notice noticeL() {
        return null;
    }


}





