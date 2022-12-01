package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyproject.board.domain.Member;
import toyproject.board.domain.Order;
import toyproject.board.dto.member.MemberRechargeDto;
import toyproject.board.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void makeOrder(Order order) {
        orderRepository.save(order);
    }

    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    public boolean passwordCheckForRecharge(MemberRechargeDto memberRechargeDto, Member member) {
        return member.getPassword().equals(memberRechargeDto.getPassword());
    }
}
