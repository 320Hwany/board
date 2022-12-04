package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.member.Member;
import toyproject.board.domain.post.Post;
import toyproject.board.dto.post.PostSaveDto;
import toyproject.board.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;

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

    public Post getPostByPostSaveDto(PostSaveDto postSaveDto) {
        Post savePost = Post.builder()
                .title(postSaveDto.getTitle())
                .contents(postSaveDto.getContents())
                .localDateTime(LocalDateTime.now())
                .build();

        return savePost;
    }

    public void setAssociation(Member loginMember, Post savePost) {
        Member member = memberService.findById(loginMember.getId());
        savePost.changeMember(member); // 연관관계 메소드를 이용해서 먼저 set 한 후 postService 로 저장해야 한다
    }
}
