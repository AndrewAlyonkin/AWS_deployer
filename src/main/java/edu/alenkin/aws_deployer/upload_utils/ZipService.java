package edu.alenkin.aws_deployer.upload_utils;

import edu.alenkin.aws_deployer.entity.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 *
 * Unpacks received with HTTP request zip-archive.
 */
@Service
public interface ZipService {
    Project unzip(MultipartFile archive) throws IOException;
}
