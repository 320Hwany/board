package toyproject.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.board.domain.OrderItems;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
}
