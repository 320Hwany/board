package toyproject.board.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.Member;
import toyproject.board.domain.Post;
import toyproject.board.repository.MemberRepository;
import toyproject.board.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    public void 회원가입_성공() {
        Member member = Member.builder()
                .email("yhwjd@naver.com")
                .username("yhwjd")
                .password("1234")
                .posts(null)
                .build();

        Member savedMember = memberService.signup(member);
        System.out.println(member);
        System.out.println(savedMember);
    }

    @Test
    public void 이름으로_회원찾기() {
        //given
        Post post1 = new Post();
        List<Post> posts1 = new ArrayList<>();
        posts1.add(post1);

        Member member = new Member("yhwjd@naver.com","userA", "1234", posts1);
        //when
        memberService.signup(member);
        Member findMemberByUsername = memberService.findByUsername("userA").get();
        //then
        assertThat(member).isEqualTo(findMemberByUsername);
        assertThat(member.getId()).isEqualTo(findMemberByUsername.getId());
        assertThat(member.getUsername()).isEqualTo(findMemberByUsername.getUsername());
        assertThat(member.getPassword()).isEqualTo(findMemberByUsername.getPassword());
        assertThat(member.getPosts()).isEqualTo(findMemberByUsername.getPosts());
    }

    @Test
    public void 아이디로_회원찾기() {
        //given
        Post post1 = new Post();
        List<Post> posts1 = new ArrayList<>();
        posts1.add(post1);

        Member member = new Member("yhwjd@naver.com","userA", "1234", posts1);
        //when
        memberService.signup(member);
        Member findMemberById = memberService.findById(member.getId());
        //then
        assertThat(member).isEqualTo(findMemberById);
        assertThat(member.getId()).isEqualTo(findMemberById.getId());
        assertThat(member.getUsername()).isEqualTo(findMemberById.getUsername());
        assertThat(member.getPassword()).isEqualTo(findMemberById.getPassword());
        assertThat(member.getPosts()).isEqualTo(findMemberById.getPosts());
    }
}