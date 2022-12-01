package toyproject.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toyproject.board.domain.*;
import toyproject.board.dto.item.ItemDto;
import toyproject.board.service.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ItemController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final ItemStorageService itemStorageService;
    private final OrderItemsService orderItemsService;

    private final OrderService orderService;

    @GetMapping("/item")

    public String itemForm(Model model) {
        model.addAttribute("itemDto", new ItemDto());
        return "item/itemHome";
    }

    @PostMapping("/item")
    public String addItem(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
                          @Valid @ModelAttribute ItemDto itemDto,
                          BindingResult bindingResult) {

        Member member = memberService.findById(loginMember.getId());

        if (bindingResult.hasErrors()) {
            return "item/itemHome";
        }

//        Item item = itemService.addItem(itemDto);
//        Order order = Order.createOrder(member, item, itemDto.getQuantity());
//        orderService.makeOrder(order);

        return "redirect:/order";
    }

    @GetMapping("/itemList")
    public String itemList(Model model) {
        List<StorageItem> storageItems = itemStorageService.findAll();
        model.addAttribute("storageItems", storageItems);
        return "item/itemList";
    }

    @GetMapping("/itemList/{id}")
    public String AddInBasketForm(@PathVariable Long id, Model model) {
        StorageItem storageItem = itemStorageService.findById(id);
        model.addAttribute("storageItem", storageItem);
        return "item/basket";
    }

    @PostMapping("/itemList/{id}")
    public String AddInBasket(@PathVariable Long id,
                              @SessionAttribute(name = "loginMember", required = false) Member loginMember,
                              @ModelAttribute ItemDto itemDto) {

        StorageItem storageItem = itemStorageService.findById(id);
        itemDto.setItemName(storageItem.getItemName());
        itemDto.setPrice(storageItem.getPrice());
        storageItem.minusQuantity(itemDto.getQuantity());

        Item item = itemService.addItem(itemDto);
        Member member = memberService.findById(loginMember.getId());
        Order order = Order.builder()
                .member(member)
                .build();

        order.changeOrder(member);

        OrderItems orderItems = new OrderItems();

        orderItems.changeItem(item);
        orderItems.changeOrder(order);

        orderService.makeOrder(order);
        itemService.save(item);
        orderItemsService.save(orderItems);
        // 연관 관계 주인 쪽을 나중에 저장 시켜야 하는건가?

        return "redirect:/";
    }
}
