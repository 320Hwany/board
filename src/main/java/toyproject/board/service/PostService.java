package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyproject.board.domain.Post;
import toyproject.board.repository.PostRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public Post save(Post post) {
        postRepository.save(post);
        return post;
    }

    public List<Post> findAll() {
        List<Post> posts = postRepository.findAll();
        return posts;
    }

    public List<Post> findByTitle(String title) {
        List<Post> posts = postRepository.findByTitle(title);
        return posts;
    }
}
