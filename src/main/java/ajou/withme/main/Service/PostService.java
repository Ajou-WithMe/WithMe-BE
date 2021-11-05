package ajou.withme.main.Service;

import ajou.withme.main.Repository.PostRepository;
import ajou.withme.main.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    public Post findPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Slice<Post> findAll(PageRequest pageRequest) {
        return postRepository.findAll(pageRequest);
    }

    public Slice<Post> findPostAllByLocation(PageRequest pageRequest, String location) {
        return postRepository.findByLocation(location, pageRequest);
    }
}
