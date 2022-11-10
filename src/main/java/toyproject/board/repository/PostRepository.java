package toyproject.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.board.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitle(String title);
}
