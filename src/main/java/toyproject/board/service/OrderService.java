package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyproject.board.domain.Order;
import toyproject.board.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void makeOrder(Order order) {
        orderRepository.save(order);
    }
}
