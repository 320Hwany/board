package toyproject.board.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
    public MemberLoginDto login(@RequestBody @Valid MemberLoginDto memberLoginDto,
                        HttpServletRequest request) {
        Member.makeSessionForLogin(request, memberLoginDto);
        return memberLoginDto;
    }
}
