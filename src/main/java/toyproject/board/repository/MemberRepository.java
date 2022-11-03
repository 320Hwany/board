package toyproject.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.board.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 메소드명 findByName 으로 했을 때 안되었다. Member 안에 username 으로 변수 선언되어있다.
    Optional<Member> findByUsername(String username);
}
