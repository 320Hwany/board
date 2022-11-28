package toyproject.board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
// JPA 가 내부에서 동적으로 객체를 생성하기 때문에 기본생성자가 필요하다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

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

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Post> posts;

    @Builder
    public Member(String email, String username, String password, List<Post> posts, LocalDateTime localDateTime) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.posts = posts;
        this.localDateTime = localDateTime;
    }

    public Member updateMember(String username, String password) {
        this.username = username;
        this.password = password;
        return this;
    }
}
