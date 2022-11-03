package toyproject.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject.board.domain.Member;
import toyproject.board.domain.MemberDto;
import toyproject.board.domain.Post;
import toyproject.board.service.MemberService;

import java.util.List;

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

        Member member = Member.builder()
                .username(memberDto.getUsername())
                .password(memberDto.getPassword())
                .build();

        memberService.signup(member);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDto memberDto, RedirectAttributes redirectAttributes) {
        Member findMember = memberService.findByUsername(memberDto.getUsername());

        if (findMember.getPassword().equals(memberDto.getPassword())) {
            Long id = findMember.getId();
            redirectAttributes.addAttribute("id", id);
            return "redirect:/home/{id}";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/home/{id}")
    public String home(@PathVariable Long id, Model model) {

        Member member = memberService.findById(id);
        List<Post> posts = member.getPosts();
        model.addAttribute("member", member);
        model.addAttribute("posts", posts);
        return "home";
    }
}
