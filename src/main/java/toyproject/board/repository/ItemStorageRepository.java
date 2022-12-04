package toyproject.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.board.domain.item.StorageItem;

public interface ItemStorageRepository extends JpaRepository<StorageItem, Long> {
}
