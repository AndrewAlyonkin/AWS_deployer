package edu.alenkin.aws_deployer.deploy;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
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

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Service
@Slf4j
public class AwsPushService implements PushService {

    private AmazonS3 s3client;

    private AWSCredentials credentials;

    public AwsPushService(@Value("${access}") String accessKey,
                          @Value("${secret}") String secretKey) {
        credentials = new BasicAWSCredentials(accessKey, secretKey);
        s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_NORTHEAST_1)
                .build();
        log.info("AwsPushService successfully created");
    }

    @Override
    public String push(Project project) {
        log.info("Pushing {} to S3", project.getName());
        File fileObj = project.getPath().toFile();
        String projectName = project.getName();
        String bucket = "alenkin" + project.getName();
        s3client.createBucket(bucket);
        TransferManager tm = TransferManagerBuilder.standard().withS3Client(s3client).build();

        MultipleFileUpload upload = tm.uploadDirectory(bucket,
                projectName + "/", fileObj, true);
        return s3client.getUrl(bucket, upload.getKeyPrefix()).toString();

    }
}
