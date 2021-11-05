package ajou.withme.main.controller.board;

import ajou.withme.main.Service.PostFileService;
import ajou.withme.main.Service.PostService;
import ajou.withme.main.Service.S3Service;
import ajou.withme.main.Service.UserService;
import ajou.withme.main.domain.Post;
import ajou.withme.main.domain.PostFile;
import ajou.withme.main.domain.User;
import ajou.withme.main.dto.board.request.SavePostRequest;
import ajou.withme.main.dto.board.request.UpdatePostStateRequest;
import ajou.withme.main.dto.board.response.GetPostDetailResponse;
import ajou.withme.main.util.JwtTokenUtil;
import ajou.withme.main.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final PostService postService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final PostFileService postFileService;
    private final S3Service s3Service;

    @PostMapping
    public ResFormat savePost(HttpServletRequest request, @RequestBody SavePostRequest savePostRequest) {
        String uid = jwtTokenUtil.getSubject(request);

        User guardian = userService.findUserByUid(uid);
        User protection = userService.findUserByUid(savePostRequest.getProtection());

        Post post = savePostRequest.toEntity(protection, guardian);

        Post savedPost = postService.savePost(post);

        for (String file:
             savePostRequest.getFiles()) {
            PostFile postFile = PostFile.builder().file(file).post(savedPost).build();
            postFileService.savePostFile(postFile);
        }

        return new ResFormat(true, 201L, "게시글 작성을 완료했습니다.");
    }

    @DeleteMapping
    public ResFormat deletePost(@RequestParam Long id) {
        postService.deletePostById(id);

        return new ResFormat(true, 201L, "게시글 삭제를 완료했습니다.");
    }

    @GetMapping
    public ResFormat getPostDetail(HttpServletRequest request, @RequestParam Long id) {
        String uid = jwtTokenUtil.getSubject(request);
        User userByUid = userService.findUserByUid(uid);

        Post post = postService.findPostById(id);

        List<PostFile> postFileList = postFileService.findFileByPost(post);

        List<String> fileUrl = new LinkedList<>();

        for (PostFile postFile:
             postFileList) {
            String url = s3Service.getFileUrl(postFile.getFile());
            fileUrl.add(url);
        }

        GetPostDetailResponse getPostDetailResponse = new GetPostDetailResponse(post, fileUrl);

        if (post.getGuardian().getUid().equals(userByUid.getUid())) {
            getPostDetailResponse.setMine(true);
        }

        return new ResFormat(true, 200L, getPostDetailResponse);
    }

    @PostMapping("/state")
    public ResFormat updatePostState(@RequestBody UpdatePostStateRequest updatePostStateRequest) {
        Post postById = postService.findPostById(updatePostStateRequest.getId());
        postById.updateState(updatePostStateRequest.getState());

        postService.savePost(postById);

        return new ResFormat(true, 201L, "게시글의 보호자 만남 상태를 변경했습니다.");
    }
}
