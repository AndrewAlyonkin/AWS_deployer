package edu.alenkin.aws_deployer.deploymanager;

import edu.alenkin.aws_deployer.exceptions.ValidationException;
import edu.alenkin.aws_deployer.deploy.AwsPushService;
import edu.alenkin.aws_deployer.entity.Project;
import edu.alenkin.aws_deployer.upload_utils.FileService;
import edu.alenkin.aws_deployer.upload_utils.UploadFileResponse;
import edu.alenkin.aws_deployer.upload_utils.ZipService;
import edu.alenkin.aws_deployer.validation_utils.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 * <p>
 * The basic implementation of {@link DeployManager}. Unpacks archive, received vith HTTP request, validates it
 * and deploys to Amazon Web Services infrastructure.
 */
@Service
@Slf4j
public class AwsDeployManager implements DeployManager {

    @Autowired
    private ZipService zipService;

    @Autowired
    ValidationService validationService;

    @Autowired
    FileService fileService;

    @Autowired
    AwsPushService awsPushService;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public UploadFileResponse upload(MultipartFile archive) throws ValidationException, IOException, InterruptedException {
        String archiveName = archive.getName();
        try {

            // Unpacking phase
            log.info("{} extracting", archiveName);
            Project project = zipService.unzip(archive);
            String projectName = project.getName();
            log.debug("Archive for {} successfully unpacked", projectName);

            // Validation phase
            log.info("{} validation", projectName);
            if (!validationService.isValid(project)) {
                log.error("Project {} is invalid", projectName);
                throw new ValidationException("Project is invalid! It must contains well-formed Dockerfile and pom.xml!");
            } else {

                // Deploying phase
                log.info("{} pushing to server", projectName);
                String fileUri = awsPushService.push(project);
                log.debug("Successfully pushed {}", projectName);

                return UploadFileResponse.createResponse()
                        .withProjectName(projectName)
                        .withUrl(fileUri)
                        .fromArchiveNamed(archiveName)
                        .withSize(project.getSize());
            }

        } finally {
            // tmp folder clearing phase
            // tmp folder will be cleaned up in a background process
            executor.execute(this::clearTmpDir);
        }
    }

    /**
     * Clear temporary folder with the project on server file system
     */
    private void clearTmpDir() {
        log.info("Temp directory clearing is started");
        try {
            fileService.clearStorageDir();
            log.info("Temporary directory is cleared!");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Failed to clear tmp directory with: {}", e.getMessage());
        }
    }
}
