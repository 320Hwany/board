package toyproject.board.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.Member;
import toyproject.board.domain.Post;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest  // @SpringBootTest 를 해야하는데 @SpringBootApplication 이라고 했다. // DB 실행하고 테스트하기
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

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
        Member findMember = memberService.signup(member);
        //then
        assertThat(member).isEqualTo(findMember);
        assertThat(member.getPosts().size()).isEqualTo(2);
    }
}