package toyproject.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject.board.domain.*;
import toyproject.board.dto.item.ItemDto;
import toyproject.board.service.*;

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
                              @RequestParam int quantity,
                              RedirectAttributes redirectAttributes) {

        StorageItem storageItem = itemStorageService.findById(id);

        if (storageItem.getQuantity() < quantity) {
            redirectAttributes.addAttribute("quantityError", true);
            return "redirect:/itemList/{id}";
        }
        storageItem.minusQuantity(quantity);

        Item item = itemService.makeItemByStorage(storageItem, quantity);
        Member member = memberService.findById(loginMember.getId());
        Order order = Order.builder()
                .member(member)
                .build();

        order.changeOrder(member);

        OrderItems orderItems = new OrderItems();
        orderItems.changeOrderAndItem(order, item);

        orderService.save(order);
        itemService.save(item);
        orderItemsService.save(orderItems);
        // 연관 관계 주인 쪽을 나중에 저장 시켜야 하는건가?

        return "redirect:/";
    }
}
