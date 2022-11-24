package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.Post;
import toyproject.board.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
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

    public Post findById(Long id) {
        Post post = postRepository.findById(id).get();
        return post;
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }
}
