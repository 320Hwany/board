package toyproject.board.domain.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.board.domain.BaseTimeEntity;
import toyproject.board.domain.item.OrderItems;
import toyproject.board.domain.member.Member;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@Table(name = "Orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems = new ArrayList<>();

    @Builder
    public Order(Member member) {
        this.member = member;
    }

    public void changeOrder(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public static Order createOrder(Member member) {
        Order order = new Order();
        order.changeOrder(member);
        return order;
    }
}
