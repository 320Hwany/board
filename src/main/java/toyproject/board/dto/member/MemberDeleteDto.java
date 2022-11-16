package toyproject.board.dto.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class MemberDeleteDto {

    @Size(min = 4, max = 12, message = "최소 4글자 최대 12글자의 비밀번호만 생성 가능합니다.")
    private String password;

    @Size(min = 4, max = 12)
    private String passwordCheck;
}
