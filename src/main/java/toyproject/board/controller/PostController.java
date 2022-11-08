package toyproject.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject.board.domain.Member;
import toyproject.board.domain.Post;
import toyproject.board.domain.PostDto;
import toyproject.board.service.MemberService;
import toyproject.board.service.PostService;

import java.util.List;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("/{id}/registration")
    public String registrationForm(@PathVariable Long id, Model model) {
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
        return "registration";
    }

    @PostMapping("/{id}/registration")
    public String registration(@PathVariable Long id, @ModelAttribute PostDto postDto,
                               RedirectAttributes redirectAttributes) {
        Member member = memberService.findById(id);

        Post savePost = Post.builder()
                .title(postDto.getTitle())
                .body(postDto.getBody())
                .build();

        savePost.setMember(member); // 연관관계 메소드를 이용해서 먼저 set 한 후 postService 로 저장해야 한다
        Post post = postService.save(savePost);

        redirectAttributes.addAttribute("id", id);
        redirectAttributes.addAttribute("statusRegistration", true);

        return "redirect:/home/{id}";
    }

    @GetMapping("/postList")
    public String postList(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "postList";
    }
}
