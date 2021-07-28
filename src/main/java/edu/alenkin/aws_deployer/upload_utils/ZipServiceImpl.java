package edu.alenkin.aws_deployer.upload_utils;

import edu.alenkin.aws_deployer.entity.Project;
import edu.alenkin.aws_deployer.properties.FileStorageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private FileStorageProperties fileProperties;

    @Override
    public Project unzip(MultipartFile archive) throws IOException {
        log.info("Trying to unzip {}", archive.getName());
        ZipInputStream inputStream = new ZipInputStream(archive.getInputStream());

        String projectDir = archive.getOriginalFilename().replace(".zip", "");
        Path path = Paths.get(fileProperties.getUploadDir()).resolve(projectDir);
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

        return new Project(projectDir, path, Files.size(path));
    }
}
