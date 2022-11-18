package toyproject.board.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String username;
    private String password;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Post> posts;

    @Builder
    public Member(String email, String username, String password, List<Post> posts) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.posts = posts;
    }

    public Member updateMember(String username, String password) {
        this.username = username;
        this.password = password;
        return this;
    }
}
