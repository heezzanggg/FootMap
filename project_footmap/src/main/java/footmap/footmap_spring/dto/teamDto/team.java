package footmap.footmap_spring.dto.teamDto;


import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class team {
    private int     t_code;
    private String  t_img;
    private String  t_name;
    private String  t_stadium;
    private int     t_vic;
    private int     t_draw;
    private int     t_lose;
    private String  t_intro;
    private String  t_cap;
    private String  u_nick;
    private int     u_code;
}
