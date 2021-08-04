package edu.alenkin.aws_deployer.upload_utils;

import edu.alenkin.aws_deployer.entity.Project;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 * <p>
 * The base implementation of {@link FileService}.
 * Stores project files in temporary folder on server file system.
 * Searches project directory in files tree no more than 1 nesting depth.
 */
@Service
@Slf4j
public class TmpFolderFileService implements FileService {

    private final String uploadDir;
    private final String buildspecPath;

    public TmpFolderFileService(@Value("${file.upload-dir}") String uploadDir,
                                @Value("${file.buildspec-path}") String buildspecPath) {
        this.uploadDir = uploadDir;
        this.buildspecPath = buildspecPath;
    }

    @Override
    public boolean isFilesExists(Project project, String... fileName) throws IOException {
        Path projectPath = project.getPath();

        if (findFiles(projectPath, fileName)) {
            log.info("Required files is successfully found!");
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
                    project.setSize((int) FileUtils.sizeOfDirectory(folder.toFile()));
                    project.setPathChanged(true);
                    return true;
                }
            }
        }
        log.info("Required files is absent!");
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
    public void clearStorageDir() throws IOException {
        FileUtils.deleteDirectory(new File(uploadDir));
    }

    @Override
    public void deleteFile(File file) throws IOException {
        FileUtils.delete(file);
    }

    @Override
    public Path putBuildSpec(Project project) throws IOException {
       return Files.copy(Paths.get(buildspecPath), project.getPath().resolve("buildspec.yml"));
    }
}
