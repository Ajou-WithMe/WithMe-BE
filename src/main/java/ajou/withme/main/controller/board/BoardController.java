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
import ajou.withme.main.dto.board.response.*;
import ajou.withme.main.util.JwtTokenUtil;
import ajou.withme.main.util.ResFormat;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    @PutMapping
    @Transactional
    public ResFormat updatePost(HttpServletRequest request, @RequestParam Long id, @RequestBody SavePostRequest savePostRequest) {
        String uid = jwtTokenUtil.getSubject(request);

        User guardian = userService.findUserByUid(uid);
        User protection = userService.findUserByUid(savePostRequest.getProtection());

        Post post = savePostRequest.toEntity(protection, guardian);
        post.setId(id);

        Post savedPost = postService.savePost(post);
        postFileService.deleteByPost(savedPost);

        for (String file:
                savePostRequest.getFiles()) {
            PostFile postFile = PostFile.builder().file(file).post(savedPost).build();
            postFileService.savePostFile(postFile);
        }

        return new ResFormat(true, 201L, "게시글 수정을 완료했습니다.");
    }

    @GetMapping("/update")
    public ResFormat getUpdateBase(@RequestParam Long id) {
        Post postById = postService.findPostById(id);
        List<PostFile> fileByPost = postFileService.findFileByPost(postById);
        List<PostFileBaseResponse> postFileBase = new LinkedList<>();

        for (PostFile postFile:
             fileByPost) {
            String fileUrl = s3Service.getFileUrl(postFile.getFile());
            PostFileBaseResponse build = PostFileBaseResponse.builder().file(postFile.getFile()).url(fileUrl).build();
            postFileBase.add(build);
        }


        GetUpdateBaseResponse getUpdateBaseResponse = new GetUpdateBaseResponse(postById, postFileBase);

        return new ResFormat(true, 200L, getUpdateBaseResponse);
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

    @GetMapping("/page")
    public ResFormat getPostPaging(@RequestParam int page, @RequestParam String location) {

        PageRequest pageRequest = PageRequest.of(page, 20, Sort.by("id").descending());

        List<PostPagingResponse> pagingResponses = new LinkedList<>();

        Page<Post> postAllByLocationState = postService.findPostAllByLocationState(pageRequest, location, 0);
        for (Post post : postAllByLocationState) {
//            0. id 1.이미지 2.타이틀 3. 마지막 목격장소 4.인상착의 5. createdAt
            PostPagingResponse curPost = new PostPagingResponse(post);

            List<PostFile> fileByPost = postFileService.findFileByPost(post);

            if (!fileByPost.isEmpty()) {
                String fileUrl = s3Service.getFileUrl(fileByPost.get(0).getFile());
                curPost.setImg(fileUrl);
            }

            pagingResponses.add(curPost);

        }

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("post", pagingResponses);
        res.put("page", postAllByLocationState.getTotalPages());

        return new ResFormat(true, 200L, res);
    }

    @GetMapping("/my")
    public ResFormat getMyPosts(HttpServletRequest request) {
        String subject = jwtTokenUtil.getSubject(request);
        User userByUid = userService.findUserByUid(subject);

        List<Post> posts = postService.findPostByGuardian(userByUid);

        List<MyPostResponse> myPostResponses = new LinkedList<>();

        for (Post post:
             posts) {
            MyPostResponse curPost = new MyPostResponse(post);

            List<PostFile> fileByPost = postFileService.findFileByPost(post);

            if (!fileByPost.isEmpty()) {
                String fileUrl = s3Service.getFileUrl(fileByPost.get(0).getFile());
                curPost.setImg(fileUrl);
            }

            myPostResponses.add(curPost);
        }

        return new ResFormat(true, 200L, myPostResponses);
    }



    @PostMapping("/test")
    public ResFormat insertPost() {

        for (int i = 0; i < 100; i++) {

            Post post = Post.builder().content("content " + i).title("title " + i).build();
            postService.savePost(post);
            
        }

        return new ResFormat(true, 201L, "데이터 생성");
    }
}
