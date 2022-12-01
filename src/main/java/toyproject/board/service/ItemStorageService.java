package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyproject.board.domain.StorageItem;
import toyproject.board.repository.ItemStorageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
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
