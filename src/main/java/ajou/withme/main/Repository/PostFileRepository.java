package ajou.withme.main.Repository;

import ajou.withme.main.domain.Post;
import ajou.withme.main.domain.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostFileRepository extends JpaRepository<PostFile, Long> {
    List<PostFile> findByPost(Post post);

    void deleteByPost(Post savedPost);
}
