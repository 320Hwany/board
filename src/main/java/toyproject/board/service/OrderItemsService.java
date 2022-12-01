package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyproject.board.domain.OrderItems;
import toyproject.board.repository.OrderItemsRepository;

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
}
