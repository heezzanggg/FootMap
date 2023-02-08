package footmap.footmap_spring.dto.JJokjiDao;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class JJokji {
    private int j_code;
    private int j_sendname;
    private int j_backname;
    private String j_cont;
    private String j_sendtime;
    private String j_backtime;
    private String j_check;
    private int j_sendteam;
    private int j_choice;
    private int u_code;
    private int j_backteam;
    private int g_code;
}
