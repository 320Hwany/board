package toyproject.board.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.Member;
import toyproject.board.domain.Post;
import toyproject.board.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest  // @SpringBootTest 를 해야하는데 @SpringBootApplication 이라고 했다. // DB 실행하고 테스트하기
@Transactional
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    // 실패 테스트도 만들기
    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Test
    public void 회원가입_성공() {
        //given
        Member member = new Member("yhwjd@naver.com","userA", "1234", null);
        //when
        Member savedMember = memberService.signup(member);
        //then
        assertThat(member).isEqualTo(savedMember);
        assertThat(member.getPosts().size()).isEqualTo(2);
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