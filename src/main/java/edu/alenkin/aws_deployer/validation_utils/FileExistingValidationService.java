package edu.alenkin.aws_deployer.validation_utils;

import edu.alenkin.aws_deployer.entity.Project;
import edu.alenkin.aws_deployer.upload_utils.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Service
public class FileExistingValidationService implements ValidationService {

    @Autowired
    private FileService fileService;

    @Value("${file.required-files}")
    private String[] requiredFiles;

    @Override
    public boolean isValid(Project project) {
        return fileService.isFilesExists(project, requiredFiles);
    }
}
