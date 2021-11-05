package ajou.withme.main.Service;

import ajou.withme.main.Repository.CommentRepository;
import ajou.withme.main.domain.Comment;
import ajou.withme.main.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment findCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> findCommentAllByPost(Post postById) {
        return commentRepository.findAllByPost(postById);
    }
}
