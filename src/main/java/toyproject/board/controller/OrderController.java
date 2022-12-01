package toyproject.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject.board.domain.Item;
import toyproject.board.domain.Member;
import toyproject.board.domain.Order;
import toyproject.board.domain.OrderItems;
import toyproject.board.dto.member.MemberRechargeDto;
import toyproject.board.service.MemberService;
import toyproject.board.service.OrderItemsService;
import toyproject.board.service.OrderService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final OrderItemsService orderItemsService;

    @GetMapping("/recharge")
    public String rechargeForm(Model model) {
        model.addAttribute("memberRechargeDto", new MemberRechargeDto());
        return "recharge/rechargeForm";
    }

    @PostMapping("/recharge")
    public String recharge(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
                           @Valid @ModelAttribute MemberRechargeDto memberRechargeDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        Member member = memberService.findById(loginMember.getId());

        if (bindingResult.hasErrors()) {
            return "recharge/rechargeForm";
        }

        if (orderService.passwordCheckForRecharge(memberRechargeDto, member)) {
            memberService.recharge(member, memberRechargeDto);
            redirectAttributes.addAttribute("statusRecharge", true);
            return "redirect:/home";
        }

        bindingResult.reject("rechargeError", new Object[]{}, null);
        return "recharge/rechargeForm";
    }

    @GetMapping("/order")
    public String orderForm(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
                            Model model, RedirectAttributes redirectAttributes) {

        Member member = memberService.findById(loginMember.getId());
        List<OrderItems> orderItemsList = orderItemsService.findAll();
        List<OrderItems> orderItemsForMember = new ArrayList<>();
        for (OrderItems orderItems : orderItemsList) {
            if (orderItems.getOrder().getMember() == member) {
                orderItemsForMember.add(orderItems);
            }
        }

        model.addAttribute("orderItemsForMember", orderItemsForMember);
        model.addAttribute("member", member);
        if (orderItemsList == null) {
            redirectAttributes.addAttribute("isPresentOrder", true);
            return "redirect:/noOrder";
            // redirectAttributes 를 썼는데 redirect 를 안하고 뷰네임을 리턴했다.
        }

        return "order/orderHome";
    }

    @PostMapping("/order")
    public String order(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
                        RedirectAttributes redirectAttributes) {

        Member member = memberService.findById(loginMember.getId());
        int price = 0;

        List<OrderItems> orderItemsList = orderItemsService.findAll();
        List<OrderItems> orderItemsForMember = new ArrayList<>();
        for (OrderItems orderItems : orderItemsList) {
            if (orderItems.getOrder().getMember() == member) {
                orderItemsForMember.add(orderItems);
            }
        }

        for (OrderItems orderItems : orderItemsForMember) {
            Item item = orderItems.getItem();
            price += item.getPrice() * item.getQuantity();
        }

        if (member.getMoney() >= price) {
            member.calculateMoney(member.getMoney() - price);
        }

        for (OrderItems orderItems : orderItemsForMember) {
            orderItemsService.delete(orderItems);
        }

        return "redirect:/home";
    }

    @GetMapping("/noOrder")
    public String orderFail() {
        return "order/noOrder";
    }
}
