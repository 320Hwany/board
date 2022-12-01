package toyproject.board.dto.item;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ItemDto {

    @NotBlank(message = "상품명을 입력해주세요")
    private String itemName;

    @Range(min = 0, max = 1000000, message = "0원 이상 100만원 이하의 금액을 입력해주세요")
    private int price;

    @Range(min = 0, max = 1000, message = "0개 이상 1000개 이하의 수량을 입력해주세요")
    private int quantity;
}
