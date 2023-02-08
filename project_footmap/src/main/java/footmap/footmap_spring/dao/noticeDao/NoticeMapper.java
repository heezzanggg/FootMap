package footmap.footmap_spring.dao.noticeDao;

import footmap.footmap_spring.dto.PageRequestDTO;
import footmap.footmap_spring.dto.noticeDto.notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface NoticeMapper {


    List<notice> selectFinfo();


    List<notice> noticeL(PageRequestDTO pageRequestDTO);

    List<notice> finfo4(int f_CODE);

    List<notice> finfo3(PageRequestDTO pageRequestDTO);

    notice getNoticeView(Integer idx);

    int getCountL(PageRequestDTO pageRequestDTO);
    int Count(PageRequestDTO pageRequestDTO);

}
