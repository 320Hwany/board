package toyproject.board.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toyproject.board.domain.member.Member;
import toyproject.board.dto.member.MemberLoginDto;
import toyproject.board.dto.member.MemberSignupDto;
import toyproject.board.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public MemberSignupDto signup(@RequestBody @Valid MemberSignupDto memberSignupDto) {

        Member member = memberService.getMemberBySignupDto(memberSignupDto);
        memberService.signup(member);
        return memberSignupDto;
    }

    @PostMapping("/login")
    public Member login(@RequestBody @Valid MemberLoginDto memberLoginDto,
                        HttpServletRequest request) {
        Member member = memberService.findByUsername(memberLoginDto.getUsername()).get();
        memberService.makeSessionForLogin(request, member);
        return member;
    }
}
