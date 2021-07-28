package edu.alenkin.aws_deployer.upload_utils;

import edu.alenkin.aws_deployer.entity.Project;
import edu.alenkin.aws_deployer.properties.FileStorageProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Service
@Slf4j
public class TmpFolderFileService implements FileService {

    @Autowired
    private FileStorageProperties fileProperties;

    @Override
    public boolean isFilesExists(Project project, String... fileName) throws IOException {
        Path projectPath = project.getPath();

        if (findFiles(projectPath, fileName)) {
            return true;
        } else {

            // searching path with project in nested folders
            for (Path folder : Files.list(projectPath)
                    .filter(Files::isDirectory)
                    .collect(Collectors.toList())) {

                log.info("FOLDER - {}", folder);
                if (findFiles(folder, fileName)) {
                    log.info("Project path and name is changed to {} and {}", folder, folder.getFileName().toFile().getName());
                    project.setPath(folder);
                    project.setName(folder.getFileName().toFile().getName());
                    return true;
                }
            }
        }

        return false;
    }

    private boolean findFiles(Path path, String... fileName) {
        for (String file : fileName) {
            Path filePath = path.resolve(file);
            if (Files.notExists(filePath)) {
                log.info("File {} in {} is absent", file, filePath);
                return false;
            }
        }
        return true;
    }

    @Override
    @SneakyThrows
    public void clearStorageDir() {
        FileUtils.cleanDirectory(new File(fileProperties.getUploadDir()));
    }
}
