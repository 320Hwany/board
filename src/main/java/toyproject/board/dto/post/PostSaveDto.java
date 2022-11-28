package toyproject.board.dto.post;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PostSaveDto {

    @NotBlank(message = "제목은 한 글자 이상이어야 합니다.")
    private String title;
    private String contents;
}
