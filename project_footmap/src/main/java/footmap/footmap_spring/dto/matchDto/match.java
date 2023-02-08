package footmap.footmap_spring.dto.matchDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class match {

    private int t_code;
    private String t_img;
    private String t_name;
    private String  t_stadium;
    private int t_vic;
    private int t_draw;
    private int t_lose;
    private String t_intro;
    private int u_code;
    private String u_nick;

}
