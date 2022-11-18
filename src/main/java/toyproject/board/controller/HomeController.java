package toyproject.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import toyproject.board.domain.Member;
import toyproject.board.service.MemberService;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index(
            @SessionAttribute(name = "loginMember", required = false) Member loginMember, Model model) {

        if (loginMember != null) {
            model.addAttribute("member", loginMember);
            return "redirect:/home";
        }
        return "index";
    }
}
