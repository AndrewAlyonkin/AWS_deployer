package edu.alenkin.aws_deployer.deploymanager;

import edu.alenkin.aws_deployer.exceptions.ValidationException;
import edu.alenkin.aws_deployer.upload_utils.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 *
 * Provides methods for deploying java projects to remote environment.
 * Will deploy only valid projects.
 */
public interface DeployManager {

    /**
     * Transforms multipartFile, received with request body of REST HTTP request,
     * validates it and deploys on remote server.
     *
     * @param archive the {@link MultipartFile} which was received with HTTP request
     * @return the {@link UploadFileResponse} which contains information about deployed project
     * @throws ValidationException if project structure breaking required validation constraints
     * @throws IOException because of some problems with server file system
     * @throws InterruptedException if archive extracting or project deploying wold be interrupted for some reason
     */
    UploadFileResponse upload(MultipartFile archive) throws ValidationException, IOException, InterruptedException;
}
