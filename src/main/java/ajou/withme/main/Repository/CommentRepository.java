package ajou.withme.main.Repository;

import ajou.withme.main.domain.Comment;
import ajou.withme.main.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post postById);
}
