package toyproject.board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    private int price;

    private int quantity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Builder
    public Item(String itemName, int price, int quantity, Order order) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
    }

    public void changeOrder(Order order) {
        this.order = order;
        order.getItems().add(this);
    }
}
