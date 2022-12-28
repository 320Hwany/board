package toyproject.board.domain.member;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import toyproject.board.domain.BaseTimeEntity;
import toyproject.board.domain.embeddable.Address;
import toyproject.board.domain.order.Order;
import toyproject.board.domain.post.Post;
import toyproject.board.dto.member.MemberDeleteDto;
import toyproject.board.dto.member.MemberLoginDto;
import toyproject.board.dto.member.MemberRechargeDto;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
// JPA 가 내부에서 동적으로 객체를 생성하기 때문에 기본생성자가 필요하다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @DateTimeFormat(pattern = "yyyy:MM:dd HH:mm:ss")
    private LocalDateTime localDateTime;

    private int money = 0;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Member(String email, String username, String password, List<Post> posts,
                  LocalDateTime localDateTime, Address address) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.posts = posts;
        this.localDateTime = localDateTime;
        this.address = address;
    }

    public Member updateMember(String username, String password) {
        this.username = username;
        this.password = password;
        return this;
    }

    public void calculateMoney(int money) {
        this.money = money;
    }

    public void recharge(MemberRechargeDto memberRechargeDto) {
        this.calculateMoney(this.getMoney() + memberRechargeDto.getMoney());
    }

    public boolean checkPasswordForLogin(MemberLoginDto memberLoginDto) {
        return this.getPassword().equals(memberLoginDto.getPassword());
    }

    public boolean passwordCheckForDelete(MemberDeleteDto memberDeleteDto) {
        return this.getPassword().equals(memberDeleteDto.getPassword()) &&
                this.getPassword().equals(memberDeleteDto.getPasswordCheck());
    }

    public static void makeSessionForLogin(HttpServletRequest request, MemberLoginDto findMember) {
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", findMember);
    }

    public static void sessionInvalidate(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
