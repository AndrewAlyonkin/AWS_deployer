package edu.alenkin.aws_deployer.validation_utils;

import edu.alenkin.aws_deployer.entity.Project;
import edu.alenkin.aws_deployer.upload_utils.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Service
@Slf4j
public class FileExistingValidationService implements ValidationService {

    @Autowired
    private FileService fileService;

    @Value("${file.required-files}")
    private String[] requiredFiles;

    @Override
    public boolean isValid(Project project) throws IOException {
        log.info("Validating {}", project.getName());
        return fileService.isFilesExists(project, requiredFiles);
    }
}
