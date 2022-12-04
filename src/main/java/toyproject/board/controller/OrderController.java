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
import toyproject.board.dto.member.MemberRechargeDto;
import toyproject.board.service.MemberService;
import toyproject.board.service.OrderItemsService;
import toyproject.board.service.OrderService;

import javax.validation.Valid;
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
        List<OrderItems> orderItemsForMember =
                orderItemsService.getOrderItemsListForMember(member, orderItemsList);

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
        List<OrderItems> orderItemsList = orderItemsService.findAll();
        List<OrderItems> orderItemsForMember =
                orderItemsService.getOrderItemsListForMember(member, orderItemsList);

        int price = orderItemsService.calculateForPay(0, orderItemsForMember);
        if (member.getMoney() >= price) {
            if (orderItemsList.size() > 0) {
                member.calculateMoney(member.getMoney() - price);
                Order order = orderItemsList.get(0).getOrder();
                orderService.deleteOrder(order);
            }
        } else if (member.getMoney() < price) {
            redirectAttributes.addAttribute("orderFail", true);
            return "redirect:/order";
        }

        return "redirect:/home";
    }

    @GetMapping("/noOrder")
    public String orderFail() {
        return "order/noOrder";
    }
}
