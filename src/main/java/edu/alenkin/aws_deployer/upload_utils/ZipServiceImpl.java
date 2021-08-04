package edu.alenkin.aws_deployer.upload_utils;

import edu.alenkin.aws_deployer.entity.Project;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Service
@Slf4j
public class ZipServiceImpl implements ZipService {

    private final String uploadDir;

    public ZipServiceImpl(@Value("${file.upload-dir}") String uploadDir) {
        this.uploadDir = uploadDir;
    }

    @Override
    public Project unzip(MultipartFile archive) throws IOException {
        String projectDir = archive.getOriginalFilename().replace(".zip", "");
        log.info("Trying to unzip {}", projectDir);

        ZipInputStream inputStream = new ZipInputStream(archive.getInputStream());

        Path path = Paths.get(uploadDir).resolve(projectDir);
        log.info("Destination path - {}", path);

        for (ZipEntry entry; (entry = inputStream.getNextEntry()) != null; ) {
            Path resolvedPath = path.resolve(entry.getName());
            log.info("Resolved path - {}", resolvedPath);

            if (!entry.isDirectory()) {
                Files.createDirectories(resolvedPath.getParent());
                Files.copy(inputStream, resolvedPath);
            } else {
                Files.createDirectories(resolvedPath);
            }
        }
        return new Project(projectDir, path, FileUtils.sizeOfDirectory(path.toFile()), false);
    }

    @Override
    public File zip(Project project) throws IOException {
        net.lingala.zip4j.ZipFile zip = new net.lingala.zip4j.ZipFile(project.getName()+".zip");
        zip.addFolder(project.getPath().toFile());
        return zip.getFile();
    }
}
