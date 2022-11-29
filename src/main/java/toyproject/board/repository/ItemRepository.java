package toyproject.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.board.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
