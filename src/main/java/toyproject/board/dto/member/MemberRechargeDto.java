package toyproject.board.dto.member;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;

@Getter
@Setter
public class MemberRechargeDto {

    @Size(min = 4, max = 12, message = "최소 4글자 최대 12글자의 비밀번호만 생성 가능합니다.")
    private String password;

    @Range(min = 0, max = 1000000, message = "0원 이상의 금액을 입력해주세요")
    private int money;
}
