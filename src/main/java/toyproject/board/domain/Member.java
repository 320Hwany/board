package toyproject.board.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String password;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Post> posts;

    @Builder
    public Member(String username, String password, List<Post> posts) {
        this.username = username;
        this.password = password;
        this.posts = posts;
    }
}
