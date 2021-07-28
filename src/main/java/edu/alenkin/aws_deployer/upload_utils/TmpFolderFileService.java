package edu.alenkin.aws_deployer.upload_utils;

import edu.alenkin.aws_deployer.entity.Project;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Service
public class TmpFolderFileService implements FileService {

    @Override
    public boolean isFilesExists(Project project, String... fileName) {
        Path projectPath = project.getPath();
        for (String file : fileName) {
            Path filePath = projectPath.resolve(file);
            if (Files.notExists(filePath)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clearStorageDir() {
// TODO
    }
}
