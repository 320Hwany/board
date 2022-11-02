package toyproject.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.board.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
