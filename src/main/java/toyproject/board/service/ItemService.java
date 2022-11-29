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

        Item saveItem = Item.builder()
                .itemName(itemDto.getItemName())
                .price(itemDto.getPrice())
                .quantity(itemDto.getQuantity())
                .build();

        Item item = itemRepository.save(saveItem);
        return item;
    }
}
