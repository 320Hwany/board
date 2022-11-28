package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject.board.domain.Member;
import toyproject.board.dto.member.MemberDeleteDto;
import toyproject.board.dto.member.MemberLoginDto;
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

    public Member getMemberBySignupDto(MemberSignupDto memberSignupDto) {
        Member member = Member.builder()
                .email(memberSignupDto.getEmail())
                .username(memberSignupDto.getUsername())
                .password(memberSignupDto.getPassword())
                .localDateTime(LocalDateTime.now())
                .build();

        return member;
    }

    public String checkSessionLogin(MemberLoginDto memberLoginDto, String redirectURL,
                                            HttpServletRequest request, Optional<Member> findMemberOptional) {
        if (findMemberOptional.isPresent()) {
            Member findMember = findMemberOptional.get();
            if (findMember.getPassword().equals(memberLoginDto.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute("loginMember", findMember);
                return "redirect:" + redirectURL;
            }
        }
        return null;
    }

    public String checkPasswordForDelete(MemberDeleteDto memberDeleteDto,
                                          RedirectAttributes redirectAttributes,
                                          HttpSession session, Member member) {
        if (member.getPassword().equals(memberDeleteDto.getPassword()) &&
                member.getPassword().equals(memberDeleteDto.getPasswordCheck())) {
            redirectAttributes.addAttribute("statusDeleteMember", true);
            memberRepository.delete(member);
            if (session != null) {
                session.invalidate();
            }
            return "redirect:/";
        }
        return null;
    }

    public void sessionInvalidate(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
