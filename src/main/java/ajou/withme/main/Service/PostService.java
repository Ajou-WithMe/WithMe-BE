package ajou.withme.main.Service;

import ajou.withme.main.Repository.PostRepository;
import ajou.withme.main.domain.Post;
import ajou.withme.main.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Post> findPostByGuardian(User userByUid) {
        return postRepository.findByGuardian(userByUid);

    }

    public List<Post> findPostAllByLocationState(PageRequest pageRequest, String location, int state) {
        return postRepository.findByLocationAndState(location, state, pageRequest);
    }
}
