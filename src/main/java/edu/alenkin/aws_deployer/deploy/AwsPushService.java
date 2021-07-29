package edu.alenkin.aws_deployer.deploy;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import edu.alenkin.aws_deployer.entity.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 * <p>
 * The base implementation of {@link PushService} that upload project files to AWS S3 storage.
 * It creates there separate bucket for each project.
 */
@Service
@Slf4j
public class AwsPushService implements PushService {

    private final AmazonS3 s3client;

    @Value("${AWS.bucketPrefix}")
    private String bucketPrefix;

    //to make the bucket names unique, the current date and time will be added to them
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM-HH.mm-");

    public AwsPushService(@Value("${access}") String accessKey,
                          @Value("${secret}") String secretKey,
                          @Value("${AWS.region}") String region) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();

        log.info("AwsPushService successfully created");
    }

    @Override
    public String push(Project project) throws InterruptedException {
        String projectName = project.getName();
        log.info("Pushing {} to S3", projectName);
        String bucketName = (LocalDateTime.now().format(formatter) + bucketPrefix
                + "." + project.getName()).toLowerCase(Locale.ROOT);

        s3client.createBucket(bucketName);
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(s3client).build();

        File uploadedFile = project.getPath().toFile();

        MultipleFileUpload upload = transferManager.uploadDirectory(bucketName, projectName, uploadedFile, true);

        log.info("Uploading {} to S3 in progress", projectName);
        upload.waitForCompletion();
        log.info("Uploading {} to S3 completed", projectName);

        return s3client.getUrl(bucketName, upload.getKeyPrefix()).toString();
    }
}
