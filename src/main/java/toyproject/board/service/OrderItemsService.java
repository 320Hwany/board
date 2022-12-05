package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toyproject.board.domain.item.Item;
import toyproject.board.domain.member.Member;
import toyproject.board.domain.item.OrderItems;
import toyproject.board.domain.order.Order;
import toyproject.board.dto.order.OrderDto;
import toyproject.board.repository.OrderItemsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemsService {

    private final OrderItemsRepository orderItemsRepository;
    private final ItemService itemService;

    public void save(OrderItems orderItems) {
        orderItemsRepository.save(orderItems);
    }

    public List<OrderItems> findAll() {
        return orderItemsRepository.findAll();
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

    public int calculateForPay(List<OrderItems> orderItemsList) {
        int price = 0;
        for (OrderItems orderItems : orderItemsList) {
            Item item = orderItems.getItem();
            price += item.getPrice() * item.getQuantity();
        }
        return price;
    }

    public List<Order> findOrdersByOrderDtoList(List<OrderItems> orderItemsList) {
        List<Order> orders = new ArrayList<>();

        for (OrderItems orderItem : orderItemsList) {
            Order order = orderItem.getOrder();
            orders.add(order);
        }
        return orders;
    }
}
