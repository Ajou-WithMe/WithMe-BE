package ajou.withme.main.Service;


import ajou.withme.main.util.ResFormat;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.NoArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@NoArgsConstructor
public class S3Service {
    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public String upload(String path, MultipartFile file) throws IOException {
        String fileName = path + file.getOriginalFilename();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        String contentType = file.getContentType();
        objectMetadata.setContentType(contentType);

        assert contentType != null;
        String[] split = contentType.split("/");

//        if (!split[0].equals("image")) {
//            return "false";
//        }
        System.out.println(contentType);

        InputStream inputStream = file.getInputStream();
        BufferedImage originalImg = ImageIO.read(inputStream);

        BufferedImage resize = Scalr.resize(originalImg, 1280);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(resize, split[1], baos);
        InputStream newInput = new ByteArrayInputStream(baos.toByteArray());


        s3Client.putObject(new PutObjectRequest(bucket, fileName, newInput, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucket, fileName).toString();
    }
}
