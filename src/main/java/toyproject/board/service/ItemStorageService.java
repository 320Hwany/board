package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.item.StorageItem;
import toyproject.board.repository.ItemStorageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemStorageService {

    private final ItemStorageRepository itemStorageRepository;

    public List<StorageItem> findAll() {
        return itemStorageRepository.findAll();
    }

    public StorageItem findById(Long id) {
        StorageItem storageItem = itemStorageRepository.findById(id).get();
        return storageItem;
    }
}
