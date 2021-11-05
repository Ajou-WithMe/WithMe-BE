package ajou.withme.main.controller.comment;

import ajou.withme.main.Repository.CommentReportRepository;
import ajou.withme.main.Service.*;
import ajou.withme.main.domain.Comment;
import ajou.withme.main.domain.CommentReport;
import ajou.withme.main.domain.Post;
import ajou.withme.main.domain.User;
import ajou.withme.main.dto.comment.request.SaveCommentRequest;
import ajou.withme.main.dto.comment.response.GetAllCommentResponse;
import ajou.withme.main.dto.commentReport.request.ReportCommentRequest;
import ajou.withme.main.util.JwtTokenUtil;
import ajou.withme.main.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final PostService postService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final CommentService commentService;
    private final CommentReportService commentReportService;

    @PostMapping
    public ResFormat saveComment(HttpServletRequest request, @RequestBody SaveCommentRequest saveCommentRequest) {
        String subject = jwtTokenUtil.getSubject(request);
        User userByUid = userService.findUserByUid(subject);
        Post postById = postService.findPostById(saveCommentRequest.getId());

        Comment comment = Comment.builder().comment(saveCommentRequest.getComment()).post(postById).user(userByUid).build();

        Comment savedComment = commentService.saveComment(comment);

        return new ResFormat(true, 201L, "댓글 등록을 완료했습니다.");
    }

    @PostMapping("/report")
    public ResFormat reportComment(HttpServletRequest request, @RequestBody ReportCommentRequest reportCommentRequest) {
        String subject = jwtTokenUtil.getSubject(request);
        User userByUid = userService.findUserByUid(subject);
        Comment comment = commentService.findCommentById(reportCommentRequest.getId());

        CommentReport commentReport = CommentReport.builder().comment(comment).msg(reportCommentRequest.getMsg()).type(reportCommentRequest.getType()).user(userByUid).build();

        commentReportService.saveCommentReport(commentReport);

        return new ResFormat(true, 201L, "댓글 신고를 완료했습니다.");

    }

    @DeleteMapping
    public ResFormat deleteComment(@RequestParam Long id) {
        commentService.deleteCommentById(id);

        return new ResFormat(true, 201L, "댓글 삭제를 완료했습니다.");

    }

//    id는 게시글 id
    @GetMapping
    public ResFormat getAllComment(HttpServletRequest request, @RequestParam Long id) {
//        1. 게시글 작성자는 모든 댓글 삭제 가능
//        2. 이 외는 본인 댓글 삭제 가능
        String uid = jwtTokenUtil.getSubject(request);
        User userByUid = userService.findUserByUid(uid);

        Post postById = postService.findPostById(id);

        boolean isMine = false;
        if (userByUid.getUid().equals(postById.getGuardian().getUid())) {
            isMine = true;
        }

        List<Comment> comments = commentService.findCommentAllByPost(postById);

        List<GetAllCommentResponse> getAllCommentResponses = new LinkedList<>();

        for (Comment comment:
             comments) {
            GetAllCommentResponse curComment = new GetAllCommentResponse(comment);

            if (isMine || userByUid.getUid().equals(comment.getUser().getUid())) {
                curComment.setDelete(true);
            }

            getAllCommentResponses.add(curComment);
        }

        return new ResFormat(true, 200L, getAllCommentResponses);

    }


}
