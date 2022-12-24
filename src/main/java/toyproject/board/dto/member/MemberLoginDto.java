package toyproject.board.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class MemberLoginDto implements Serializable {

    @Size(min = 4, max = 12, message = "최소 4글자 최대 12글자의 아이디만 생성 가능합니다.")
    private String username;

    @Size(min = 4, max = 12, message = "최소 4글자 최대 12글자의 비밀번호만 생성 가능합니다.")
    private String password;
}
