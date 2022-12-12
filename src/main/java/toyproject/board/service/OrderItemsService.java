package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.item.Item;
import toyproject.board.domain.member.Member;
import toyproject.board.domain.item.OrderItems;
import toyproject.board.domain.order.Order;
import toyproject.board.repository.OrderItemsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderItemsService {

    private final OrderItemsRepository orderItemsRepository;
    private final ItemService itemService;

    @Transactional
    public void save(OrderItems orderItems) {
        orderItemsRepository.save(orderItems);
    }

    public List<OrderItems> findAll() {
        return orderItemsRepository.findAll();
    }

    public List<OrderItems> getOrderItemsListForMember(Member member, List<OrderItems> orderItemsList) {
        List<OrderItems> orderItemsForMember = new ArrayList<>();

        orderItemsList.stream()
                .filter(orderItems -> orderItems.getOrder().getMember() == member)
                .forEach(orderItems -> orderItemsForMember.add(orderItems));

        return orderItemsForMember;
    }

    public int calculateForPay(List<OrderItems> orderItemsList) {
        return orderItemsList.stream().map(OrderItems::getItem)
                .mapToInt(Item::calculatePrice)
                .sum();
    }

    public List<Order> findOrdersByOrderDtoList(List<OrderItems> orderItemsList) {
        List<Order> orders = new ArrayList<>();
        orderItemsList.stream()
                .forEach(orderItems -> orders.add(orderItems.getOrder()));

        return orders;
    }
}
