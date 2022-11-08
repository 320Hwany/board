package toyproject.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.Member;
import toyproject.board.domain.Post;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest  // @SpringBootTest 를 해야하는데 @SpringBootApplication 이라고 했다. // DB 실행하고 테스트하기
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void 회원가입() {
        //given
        Post post1 = new Post();
        Post post2 = new Post();

        List<Post> posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);

        Member member = new Member("userA", "1234", posts);
        //when
        Member savedMember = memberService.signup(member);
        //then
        assertThat(member).isEqualTo(savedMember);
        assertThat(member.getPosts().size()).isEqualTo(2);
    }

    @Test
    public void 중복회원검증() {
        //given
        Post post1 = new Post();
        Post post2 = new Post();

        List<Post> posts1 = new ArrayList<>();
        List<Post> posts2 = new ArrayList<>();
        posts1.add(post1);
        posts2.add(post2);

        Member memberA = new Member("userA", "1234", posts1);
        Member memberB = new Member("userA", "123456", posts2);
        //when
        memberService.signup(memberA);
        //then
        assertThrows(IllegalStateException.class,
                () -> memberService.signup(memberB));
    }

    @Test
    public void 이름으로_회원찾기() {
        //given
        Post post1 = new Post();
        List<Post> posts1 = new ArrayList<>();
        posts1.add(post1);

        Member member = new Member("userA", "1234", posts1);
        //when
        memberService.signup(member);
        Member findMemberByUsername = memberService.findByUsername("userA");
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

        Member member = new Member("userA", "1234", posts1);
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