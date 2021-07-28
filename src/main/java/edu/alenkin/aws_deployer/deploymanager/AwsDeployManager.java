package edu.alenkin.aws_deployer.deploymanager;

import edu.alenkin.aws_deployer.ValidationException;
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

    private final static ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public UploadFileResponse upload(MultipartFile archive) throws ValidationException, IOException {
        Project project = unZip(archive);
        String projectName = project.getName();
        log.debug("Archive for {} successfully unpacked", projectName);
        if (!isValid(project)) {
            log.error("Project {} is invalid", project.getName());
            clearTmpDir();
            throw new ValidationException("Project is invalid! It must contains well-formed Dockerfile and pom.xml!");
        } else {
            String fileUri = push(project);
            log.debug("Successfully pushed {}", projectName);
            UploadFileResponse result = new UploadFileResponse(projectName, fileUri, projectName, project.getSize());
            executor.execute(this::clearTmpDir);
            return result;
        }
    }

    private void clearTmpDir() {
        log.debug("Clear temp directory on server");
        log.info("Temp directory clearing is started");
        try {
            fileService.clearStorageDir();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Temporary directory is clear!");
    }

    private Project unZip(MultipartFile archive) throws IOException {
        return zipService.unzip(archive);
    }

    private boolean isValid(Project project) throws IOException {
        return validationService.isValid(project);
    }

    private String push(Project project) {
        return null;
    }

}
