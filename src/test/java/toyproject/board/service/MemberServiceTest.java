package toyproject.board.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.member.Member;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Nested
    @DisplayName("회원가입 ")
    class Save {
        @Test
        @DisplayName("성공")
        void 회원가입_성공() {
            //given
            Member member = Member.builder()
                    .email("yhwjd@naver.com")
                    .username("yhwjd")
                    .password("1234")
                    .posts(null)
                    .localDateTime(LocalDateTime.now())
                    .address(null)
                    .build();

            //when
            Member savedMember = memberService.signup(member);
            Member findMember = memberService.findById(savedMember.getId());
            //then
            assertThat(member).isEqualTo(findMember);
            // 같은 Transaction 안에 있어야만 성공한다.
        }

        @Test
        @DisplayName("이미 아이디가 존재하면 회원가입을 할 수 없다.")
        void 회원가입_실패() {
            //given
            Member member = Member.builder()
                    .email("yhwjd@naver.com")
                    .username("yhwjd")
                    .password("1234")
                    .posts(null)
                    .localDateTime(LocalDateTime.now())
                    .address(null)
                    .build();
            //when

            //then
        }
    }

//    @Test
//    public void 이름으로_회원찾기() {
//        //given
//        Post post1 = new Post();
//        List<Post> posts1 = new ArrayList<>();
//        posts1.add(post1);
//
//        Member member = new Member("yhwjd@naver.com","userA", "1234", posts1);
//        //when
//        memberService.signup(member);
//        Member findMemberByUsername = memberService.findByUsername("userA").get();
//        //then
//        assertThat(member).isEqualTo(findMemberByUsername);
//        assertThat(member.getId()).isEqualTo(findMemberByUsername.getId());
//        assertThat(member.getUsername()).isEqualTo(findMemberByUsername.getUsername());
//        assertThat(member.getPassword()).isEqualTo(findMemberByUsername.getPassword());
//        assertThat(member.getPosts()).isEqualTo(findMemberByUsername.getPosts());
//    }
//
//    @Test
//    public void 아이디로_회원찾기() {
//        //given
//        Post post1 = new Post();
//        List<Post> posts1 = new ArrayList<>();
//        posts1.add(post1);
//
//        Member member = new Member("yhwjd@naver.com","userA", "1234", posts1);
//        //when
//        memberService.signup(member);
//        Member findMemberById = memberService.findById(member.getId());
//        //then
//        assertThat(member).isEqualTo(findMemberById);
//        assertThat(member.getId()).isEqualTo(findMemberById.getId());
//        assertThat(member.getUsername()).isEqualTo(findMemberById.getUsername());
//        assertThat(member.getPassword()).isEqualTo(findMemberById.getPassword());
//        assertThat(member.getPosts()).isEqualTo(findMemberById.getPosts());
//    }
}