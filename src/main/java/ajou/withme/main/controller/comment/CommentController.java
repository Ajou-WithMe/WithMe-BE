package ajou.withme.main.controller.comment;

import ajou.withme.main.Service.*;
import ajou.withme.main.domain.Comment;
import ajou.withme.main.domain.Post;
import ajou.withme.main.domain.User;
import ajou.withme.main.dto.comment.request.SaveCommentRequest;
import ajou.withme.main.util.JwtTokenUtil;
import ajou.withme.main.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final PostService postService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final CommentService commentService;
    @PostMapping
    public ResFormat saveComment(HttpServletRequest request, @RequestBody SaveCommentRequest saveCommentRequest) {
        String subject = jwtTokenUtil.getSubject(request);
        User userByUid = userService.findUserByUid(subject);
        Post postById = postService.findPostById(saveCommentRequest.getId());

        Comment comment = Comment.builder().comment(saveCommentRequest.getComment()).post(postById).user(userByUid).build();

        Comment savedComment = commentService.saveComment(comment);

        return new ResFormat(true, 201L, "댓글 등록을 완료했습니다.");

    }
}
