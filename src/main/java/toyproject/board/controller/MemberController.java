package toyproject.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.board.domain.Member;
import toyproject.board.domain.MemberDto;
import toyproject.board.service.MemberService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    // @ModelAttribute 는 @Setter 있어야 한다!!!
    // Member 가 @Id 있기 때문에 Dto 만들어서 builder
    @PostMapping("/signup")
    public String join(@ModelAttribute MemberDto memberDto) {

        log.info("username={}", memberDto.getUsername());
        Member member = Member.builder()
                .username(memberDto.getUsername())
                .password(memberDto.getPassword())
                .build();

        memberService.signup(member);
        return "redirect:/";
    }
}
