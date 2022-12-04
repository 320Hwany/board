package toyproject.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.board.domain.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
