package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyproject.board.domain.Item;
import toyproject.board.dto.item.ItemDto;
import toyproject.board.repository.ItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item addItem(ItemDto itemDto) {

        Item item = Item.builder()
                .itemName(itemDto.getItemName())
                .price(itemDto.getPrice())
                .quantity(itemDto.getQuantity())
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
}
