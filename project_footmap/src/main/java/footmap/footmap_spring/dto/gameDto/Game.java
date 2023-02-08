package footmap.footmap_spring.dto.gameDto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Game {

    private int g_code; //경기코드
    private int g_dung; //등록팀 코드
    private int g_search; //신청팀 코드
    private String g_date; //경기일자
    private String g_time; //경기시간
    private String g_peo; //경기인원수
    private int g_magam; //마감여부 1:매칭전 2:매칭성공 3:매칭실패
    private String g_intro; //경기 소개
    private String f_name; //구장 이름
    private String f_area; //구장 지명
    private String t_name; //팀이름
    private int f_code; //구장코드

}
