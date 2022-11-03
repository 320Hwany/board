package toyproject.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.board.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
