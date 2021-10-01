package ajou.withme.main.controller.file;

import ajou.withme.main.Service.S3Service;
import ajou.withme.main.util.JwtTokenUtil;
import ajou.withme.main.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final S3Service s3Service;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/profile")
    public ResFormat uploadProfile(HttpServletRequest request, MultipartFile file) throws IOException {
        String uid = jwtTokenUtil.getSubject(request);

        String path = "profile/" + uid + "/";

        String url = s3Service.upload(path, file);

        return new ResFormat(true, 201L, url);
    }
}
