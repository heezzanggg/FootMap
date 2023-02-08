package footmap.footmap_spring.dto.noticeDto;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class notice {
    private String del_chk;
    private int idx;
    private String n_id;
    private String n_cont;
    private String n_writer;
    private String n_title;
    private Timestamp n_regdate;
    private int n_count;
    private int F_CODE;
    private String   F_AREA;
    private String  F_NAME;
    private String   F_ADD;
    private String F_SOU;
    private String   F_TEL;
    private String F_IMG;
    //  private String G_PEO;
}
