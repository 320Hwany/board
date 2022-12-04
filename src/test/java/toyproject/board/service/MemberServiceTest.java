package toyproject.board.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import toyproject.board.domain.member.Member;
import toyproject.board.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Nested
    @DisplayName("회원가입 ")
    class Save {
        @Test
        @DisplayName("성공")
        void 회원가입_성공() {
            Member member = Member.builder()
                    .email("yhwjd@naver.com")
                    .username("yhwjd")
                    .password("1234")
                    .posts(null)
                    .build();

            System.out.println(memberRepository);
            System.out.println(memberService.signup(member));
            System.out.println(memberService.findByUsername(member.getUsername()));
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