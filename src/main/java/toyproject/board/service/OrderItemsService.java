package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyproject.board.domain.item.Item;
import toyproject.board.domain.member.Member;
import toyproject.board.domain.item.OrderItems;
import toyproject.board.repository.OrderItemsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemsService {

    private final OrderItemsRepository orderItemsRepository;

    public void save(OrderItems orderItems) {
        orderItemsRepository.save(orderItems);
    }

    public List<OrderItems> findAll() {
        return orderItemsRepository.findAll();
    }

    public void delete(OrderItems orderItems) {
        orderItemsRepository.delete(orderItems);
    }

    public List<OrderItems> getOrderItemsListForMember(Member member, List<OrderItems> orderItemsList) {
        List<OrderItems> orderItemsForMember = new ArrayList<>();
        for (OrderItems orderItems : orderItemsList) {
            if (orderItems.getOrder().getMember() == member) {
                orderItemsForMember.add(orderItems);
            }
        }
        return orderItemsForMember;
    }

    public int calculateForPay(int price, List<OrderItems> orderItemsForMember) {
        for (OrderItems orderItems : orderItemsForMember) {
            Item item = orderItems.getItem();
            price += item.getPrice() * item.getQuantity();
        }
        return price;
    }
}
