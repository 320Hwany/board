package toyproject.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.board.domain.item.Item;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    
    Optional<Item> findById(Long id);
}
