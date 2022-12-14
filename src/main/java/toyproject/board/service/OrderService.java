package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.member.Member;
import toyproject.board.domain.order.Order;
import toyproject.board.dto.member.MemberRechargeDto;
import toyproject.board.repository.OrderRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public void save(Order order) {
        orderRepository.save(order);
    }

    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    public boolean passwordCheckForRecharge(MemberRechargeDto memberRechargeDto, Member member) {
        return member.getPassword().equals(memberRechargeDto.getPassword());
    }
}
