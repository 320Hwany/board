package toyproject.board.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@Table(name = "Orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", orphanRemoval = true)
    private List<Item> items = new ArrayList<>();
    // new ArrayList<>() 를 해주어야 NPE 발생하지 않는다.

    public static Order createOrder(Member member, Item item) {
        Order order = member.getOrder();
        if (member.getOrder() == null) {
            Order makeOrder = new Order();
            makeOrder.changeOrder(member);
            item.changeOrder(makeOrder);
            return makeOrder;
        }
        item.changeOrder(order);
        return order;
    }

    public void changeOrder(Member member) {
        this.member = member;
        member.connectOrder(this);
    }
}
