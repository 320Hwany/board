package toyproject.board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class OrderItems {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_items_id")
    private Long id;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public OrderItems(Order order, Item item) {
        this.order = order;
        this.item = item;
    }

    public void changeOrder(Order order) {
        this.order = order;
        order.getOrderItemsList().add(this);
    }

    public void changeItem(Item item) {
        this.item = item;
        item.getOrderItemsList().add(this);
    }
}
