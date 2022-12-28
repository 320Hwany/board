package toyproject.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject.board.domain.member.Member;
import toyproject.board.domain.item.OrderItems;
import toyproject.board.domain.order.Order;
import toyproject.board.dto.member.MemberLoginDto;
import toyproject.board.dto.member.MemberRechargeDto;
import toyproject.board.dto.order.OrderDto;
import toyproject.board.service.MemberService;
import toyproject.board.service.OrderItemsService;
import toyproject.board.service.OrderService;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final OrderItemsService orderItemsService;

    @ModelAttribute("orderDtoList")
    public Map<Long, OrderItems> orderDto(
            @SessionAttribute(name = "loginMember", required = false) MemberLoginDto loginMember) {

        Member member = memberService.findByUsername(loginMember.getUsername()).get();
        Map<Long, OrderItems> orderDtoList = new LinkedHashMap<>();
        List<OrderItems> orderItemsList = orderItemsService.findAll();
        List<OrderItems> orderItemsForMember =
                orderItemsService.getOrderItemsListForMember(member, orderItemsList);

        orderItemsForMember.stream()
                .forEach(orderItems -> orderDtoList.put(orderItems.getId(), orderItems));
        return orderDtoList;
    }


    @GetMapping("/recharge")
    public String rechargeForm(Model model) {
        model.addAttribute("memberRechargeDto", new MemberRechargeDto());
        return "recharge/rechargeForm";
    }

    @PostMapping("/recharge")
    public String recharge(@SessionAttribute(name = "loginMember", required = false) MemberLoginDto loginMember,
                           @Valid @ModelAttribute MemberRechargeDto memberRechargeDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        Member member = memberService.findByUsername(loginMember.getUsername()).get();

        if (bindingResult.hasErrors()) {
            return "recharge/rechargeForm";
        }

        if (orderService.passwordCheckForRecharge(memberRechargeDto, member)) {
            member.recharge(memberRechargeDto);
            redirectAttributes.addAttribute("Recharge", true);
            return "redirect:/home";
        }

        bindingResult.reject("GlobalRechargeError", new Object[]{}, null);
        return "recharge/rechargeForm";
    }

    @GetMapping("/order")
    public String orderForm(@SessionAttribute(name = "loginMember", required = false) MemberLoginDto loginMember,
                            Model model, RedirectAttributes redirectAttributes) {

        Member member = memberService.findByUsername(loginMember.getUsername()).get();

        List<OrderItems> orderItemsList = orderItemsService.findAll();
        model.addAttribute("member", member);
        if (orderItemsList == null) {
            redirectAttributes.addAttribute("noOrderError", true);
            return "redirect:/noOrder";
            // redirectAttributes 를 썼는데 redirect 를 안하고 뷰네임을 리턴했다.
        }

        model.addAttribute("orderDto", new OrderDto());

        return "order/orderHome";
    }

    @PostMapping("/order")
    public String order(@SessionAttribute(name = "loginMember", required = false) MemberLoginDto loginMember,
                        @ModelAttribute OrderDto orderDto,
                        RedirectAttributes redirectAttributes) {

        Member member = memberService.findByUsername(loginMember.getUsername()).get();
        List<OrderItems> orderItemsList = orderDto.getOrderItemsList();

        int price = orderItemsService.calculateForPay(orderItemsList);
        if (member.getMoney() >= price) {
            member.calculateMoney(member.getMoney() - price);
            List<Order> orders = orderItemsService.findOrdersByOrderDtoList(orderItemsList);
            orders.stream().forEach(order -> orderService.deleteOrder(order));
        } else if (member.getMoney() < price) {
            redirectAttributes.addAttribute("lackMoneyError", true);
            return "redirect:/order";
        }

        return "redirect:/home";
    }

    @GetMapping("/noOrder")
    public String orderFail() {
        return "order/noOrder";
    }
}
