package toyproject.board.dto.member;

import lombok.Getter;
import lombok.Setter;
import toyproject.board.domain.embeddable.Address;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class MemberSignupDto {

    @Email
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @Size(min = 4, max = 12, message = "최소 4글자 최대 12글자의 아이디만 생성 가능합니다.")
    private String username;

    @Size(min = 4, max = 12, message = "최소 4글자 최대 12글자의 비밀번호만 생성 가능합니다.")
    private String password;

    @NotBlank(message = "주소를 입력해주세요")
    private String address;
}
