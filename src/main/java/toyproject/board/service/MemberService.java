package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.member.Member;
import toyproject.board.dto.member.MemberDeleteDto;
import toyproject.board.dto.member.MemberLoginDto;
import toyproject.board.dto.member.MemberRechargeDto;
import toyproject.board.dto.member.MemberSignupDto;
import toyproject.board.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member signup(Member member) {
        Member saveMember = memberRepository.save(member);
        return saveMember;
    }

    public Optional<Member> findByUsername(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);
        return member;
    }

    public Member findById(Long id) {
        Member member = memberRepository.findById(id).get();
        return member;
    }

    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }

    public void recharge(Member member, MemberRechargeDto memberRechargeDto) {
        member.calculateMoney(member.getMoney() + memberRechargeDto.getMoney());
    }

    public Member getMemberBySignupDto(MemberSignupDto memberSignupDto) {
        Member member = Member.builder()
                .email(memberSignupDto.getEmail())
                .username(memberSignupDto.getUsername())
                .password(memberSignupDto.getPassword())
                .localDateTime(LocalDateTime.now())
                .build();

        return member;
    }

    public boolean checkPasswordForLogin(MemberLoginDto memberLoginDto, Member findMember) {
        return findMember.getPassword().equals(memberLoginDto.getPassword());
    }

    public void makeSessionForLogin(HttpServletRequest request, Member findMember) {
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", findMember);
    }

    public boolean passwordCheckForDelete(MemberDeleteDto memberDeleteDto, Member member) {
        return member.getPassword().equals(memberDeleteDto.getPassword()) &&
                member.getPassword().equals(memberDeleteDto.getPasswordCheck());
    }

    public void sessionInvalidate(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
