package edu.alenkin.aws_deployer.deploymanager;

import edu.alenkin.aws_deployer.ValidationException;
import edu.alenkin.aws_deployer.entity.Project;
import edu.alenkin.aws_deployer.upload_utils.UploadFileResponse;
import edu.alenkin.aws_deployer.upload_utils.ZipService;
import edu.alenkin.aws_deployer.validation_utils.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Service
@Slf4j
public class AwsDeployManager implements DeployManager{

    @Autowired
    private ZipService zipService;

    @Autowired
    ValidationService validationService;

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
           String fileUri =  push(project);
           log.debug("Successfully pushed {}", projectName);
            return new UploadFileResponse(projectName, fileUri, projectName, project.getSize());
        }
    }

    private void clearTmpDir() {
        log.debug("Clear tem directory on server");

    }

    private Project unZip(MultipartFile archive) throws IOException {
        return zipService.unzip(archive);
    }

    private boolean isValid(Project project) {
        return validationService.isValid(project);
    }

    private String push(Project project) {
        return null;
    }

}
