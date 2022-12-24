package toyproject.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import toyproject.board.domain.member.Member;
import toyproject.board.dto.member.MemberLoginDto;
import toyproject.board.service.MemberService;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index(
            @SessionAttribute(name = "loginMember", required = false) MemberLoginDto loginMember, Model model) {

        if (loginMember != null) {
            Member member = memberService.findByUsername(loginMember.getUsername()).get();
            model.addAttribute("member", member);
            return "redirect:/home";
        }
        return "index";
    }
}
