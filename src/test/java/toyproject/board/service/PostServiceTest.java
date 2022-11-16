package toyproject.board.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.board.domain.Member;
import toyproject.board.domain.Post;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class PostServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private PostService postService;

    @Test
    public void 게시글_등록() {
        //given
        Post post = new Post("hello", "helloWorld");

        List<Post> posts = new ArrayList<>();
        posts.add(post);

        Member member = new Member("yhwjd@naver.com","userA", "1234", posts);
        //when
        memberService.signup(member);
        post.setMember(member);
        Post savedPost = postService.save(post);
        //then
        assertThat(post).isEqualTo(savedPost);
        assertThat(post.getMember()).isEqualTo(savedPost.getMember());
        assertThat(savedPost.getTitle()).isEqualTo("hello");
        assertThat(savedPost.getBody()).isEqualTo("helloWorld");
    }

    @Test
    public void 모든게시글_찾기() {
        //given
        Post post1 = new Post("hello1", "helloWorld1");
        Post post2 = new Post("hello2", "helloWorld2");
        Post post3 = new Post("hello3", "helloWorld3");

        //when
        List<Post> posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        //then
        assertThat(posts.size()).isEqualTo(3);
    }
}