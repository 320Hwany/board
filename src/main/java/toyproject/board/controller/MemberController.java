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
import toyproject.board.dto.member.*;
import toyproject.board.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("memberSignupDto", new MemberSignupDto());
        return "member/signup";
    }

    // @ModelAttribute 는 @Setter 있어야 한다!!! Dto @Setter 없앨 수가 있나...
    // Member 가 @Id 있기 때문에 Dto 만들어서 builder
    @PostMapping("/signup")
    public String join(@Valid @ModelAttribute MemberSignupDto memberSignupDto, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "member/signup";
        }
        Member member = memberService.getMemberBySignupDto(memberSignupDto);

        if (member.getAddress() == null) {
            bindingResult.reject("GlobalAddressError", new Object[]{}, null);
            return "member/signup";
        }
        Optional<Member> findMember = memberService.findByUsername(memberSignupDto.getUsername());
        if (findMember.isPresent()) {
            bindingResult.reject("GlobalSignupError", new Object[]{}, null);
            return "member/signup";
        }

        memberService.signup(member);
        redirectAttributes.addAttribute("statusLoginSuccess", true);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("memberLoginDto", new MemberLoginDto());
        return "/member/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute MemberLoginDto memberLoginDto,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/home") String redirectURL,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "/member/login";
        }
        Optional<Member> findMemberOptional = memberService.findByUsername(memberLoginDto.getUsername());
        if (findMemberOptional.isPresent()) {
            Member findMember = findMemberOptional.get();
            if (memberService.checkPasswordForLogin(memberLoginDto, findMember)) {
                memberService.makeSessionForLogin(request, findMember);
                return "redirect:" + redirectURL;
            }
        }
        // validation 을 만족하지만 존재하지 않는 아이디
        bindingResult.reject("GlobalLoginError", new Object[]{}, null);
        return "/member/login";
    }

    @GetMapping("/home")
    public String home(
            @SessionAttribute(name = "loginMember", required = false) Member loginMember, Model model) {
        // 동시 접속시 세션 문제?
        if (loginMember == null) {
            return "redirect:/";
        }

        Member member = memberService.findById(loginMember.getId());
        List<Post> posts = member.getPosts();
        model.addAttribute("member", member);
        model.addAttribute("posts", posts);
        return "member/home";
    }

    @GetMapping("/deleteMember")
    public String deleteForm(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
                             Model model) {

        Member member = memberService.findById(loginMember.getId());
        model.addAttribute("member", member);
        model.addAttribute("memberDeleteDto", new MemberDeleteDto());
        return "member/deleteMember";
    }

    @PostMapping("/deleteMember") // form method post 설정안함
    public String deleteMember(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
                               @ModelAttribute MemberDeleteDto memberDeleteDto,
                               BindingResult bindingResult,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes) {

        HttpSession session = request.getSession(false);
        Member member = memberService.findById(loginMember.getId());
        // String == 비교 말고 equals 사용해야함. String 은 불변 객체

        if (memberService.passwordCheckForDelete(memberDeleteDto, member)) {
            redirectAttributes.addAttribute("DeleteMember", true);
            memberService.deleteMember(member);
            if (session != null) {
                session.invalidate();
            }
            return "redirect:/";
        }

        redirectAttributes.addAttribute("statusDeleteFail", true);
        return "redirect:/deleteMember";
    }

    @GetMapping("/updateMember")
    public String updateMember(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
                               Model model) {
        Member member = memberService.findById(loginMember.getId());
        model.addAttribute("member", member);
        model.addAttribute("memberUpdateDto", new MemberUpdateDto());
        return "member/updateMember";
    }

    @PostMapping("/updateMember")
    public String updateMember(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
                               @Valid @ModelAttribute MemberUpdateDto memberUpdateDto,
                               BindingResult bindingResult,
                               HttpServletRequest request,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        Member member = memberService.findById(loginMember.getId());

        if (bindingResult.hasErrors()) {
            model.addAttribute("member", member);
            return "member/updateMember";
        }
        member.updateMember(memberUpdateDto.getUsername(), memberUpdateDto.getPassword());
        redirectAttributes.addAttribute("UpdateMember", true);

        memberService.sessionInvalidate(request);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logoutForm(HttpServletRequest request) {
        memberService.sessionInvalidate(request);
        return "redirect:/";
    }
}