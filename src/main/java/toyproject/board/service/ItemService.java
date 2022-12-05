package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyproject.board.domain.item.Item;
import toyproject.board.domain.item.StorageItem;
import toyproject.board.repository.ItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item makeItemByStorage(StorageItem storageItem, int quantity) {
        Item item = Item.builder()
                .itemName(storageItem.getItemName())
                .price(storageItem.getPrice())
                .quantity(quantity)
                .build();

        return item;
    }

    public void save(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findAll() {
        List<Item> items = itemRepository.findAll();
        return items;
    }

    public Item findById(Long id) {
        Item item = itemRepository.findById(id).get();
        return item;
    }

    public Item findByItemName(String name) {
        Item item = itemRepository.findByItemName(name).get();
        return item;
    }
}
