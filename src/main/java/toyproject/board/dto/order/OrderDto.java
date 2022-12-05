package toyproject.board.dto.order;

import lombok.Getter;
import lombok.Setter;
import toyproject.board.domain.item.OrderItems;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderDto {

    private List<OrderItems> orderItemsList = new ArrayList<>();
}
