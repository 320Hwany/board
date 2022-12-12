package toyproject.board.domain.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.board.domain.order.Order;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성 메소드를 만들어서 protected 로 유지할 수 있다
@Getter
public class OrderItems {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_items_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public OrderItems(Order order, Item item) {
        this.order = order;
        this.item = item;
    }

    public void changeOrderAndItem(Order order, Item item) {
        this.order = order;
        order.getOrderItems().add(this);
        this.item = item;
    }

    public static OrderItems createOrderItems(Order order, Item item) {
        OrderItems orderItems = new OrderItems();
        orderItems.changeOrderAndItem(order, item);
        return orderItems;
    }
}
