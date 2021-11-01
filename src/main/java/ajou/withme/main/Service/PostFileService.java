package ajou.withme.main.Service;

import ajou.withme.main.Repository.PostFileRepository;
import ajou.withme.main.domain.Post;
import ajou.withme.main.domain.PostFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostFileService {

    private final PostFileRepository postFileRepository;

    public PostFile savePostFile(PostFile postFile) {
        return postFileRepository.save(postFile);
    }
}
