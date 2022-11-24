package toyproject.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject.board.domain.Member;
import toyproject.board.domain.Post;
import toyproject.board.dto.post.PostDto;
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

    @GetMapping("/registration")
    public String registrationForm(
            @SessionAttribute(name = "loginMember", required = false) Member member,
            Model model) {

        model.addAttribute("member", member);
        return "post/registration";
    }

    @PostMapping("/registration")
    public String registration(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
                               @ModelAttribute PostDto postDto,
                               RedirectAttributes redirectAttributes) {
        Post savePost = Post.builder()
                .title(postDto.getTitle())
                .body(postDto.getBody())
                .build();

        Member member = memberService.findByUsername(loginMember.getUsername()).get();
        savePost.setMember(member); // 연관관계 메소드를 이용해서 먼저 set 한 후 postService 로 저장해야 한다
        Post post = postService.save(savePost);

        redirectAttributes.addAttribute("statusRegistration", true);

        return "redirect:/home";
    }

    @GetMapping("/postList")
    public String postList(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "post/postList";
    }

    // redirect 다시 해보기
    @GetMapping("/findPosts")
    public String findPostsForm() {
        return "post/findPosts";
    }

    @PostMapping("/findPosts")
    public String findPost(@RequestParam String title, Model model,
                           RedirectAttributes redirectAttributes) {
        List<Post> posts = postService.findByTitle(title);

        if (posts.size() == 0) {
            redirectAttributes.addAttribute("statusNoList", true);
            return "redirect:/home/findPosts";
        }
        // redirectAttributes 는 redirect:/ 를 return 할 때 사용
        model.addAttribute("posts", posts);

        return "post/findPosts";
    }

    @GetMapping("/postHome/{id}")
    public String postHome(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "post/postHome";
    }

    @PostMapping("/postHome/{id}/deletePost")
    public String deletePost(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Post post = postService.findById(id);
        postService.delete(post);
        redirectAttributes.addAttribute("postDeleteStatus", true);

        return "redirect:/";
    }
}
