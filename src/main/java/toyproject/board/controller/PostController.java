package toyproject.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject.board.domain.member.Member;
import toyproject.board.domain.post.Post;
import toyproject.board.dto.member.MemberLoginDto;
import toyproject.board.dto.post.PostSaveDto;
import toyproject.board.service.MemberService;
import toyproject.board.service.PostService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @GetMapping("/registration")
    public String registrationForm(
            @SessionAttribute(name = "loginMember", required = false) Member member,
            Model model) {

        model.addAttribute("member", member);
        model.addAttribute("postSaveDto", new PostSaveDto());
        return "post/registration";
    }

    // th:field 를 사용하여 valid 조건에 맞지 않을 때 원래 값 그대로 보존할 수 있다.
    @PostMapping("/registration")
    public String registration(
            @SessionAttribute(name = "loginMember", required = false) MemberLoginDto loginMember,
            @Valid @ModelAttribute PostSaveDto postSaveDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "/post/registration";
        }
        Post savePost = postService.getPostByPostSaveDto(postSaveDto);
        postService.setAssociation(loginMember, savePost);

        redirectAttributes.addAttribute("postRegistration", true);

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
            redirectAttributes.addAttribute("noListError", true);
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
    public String deletePost(
            @SessionAttribute(name = "loginMember", required = false) MemberLoginDto loginMember,
            @PathVariable Long id, RedirectAttributes redirectAttributes) {

        postService.deletePost(loginMember, id);
        redirectAttributes.addAttribute("postDelete", true);

        return "redirect:/";
    }
}
