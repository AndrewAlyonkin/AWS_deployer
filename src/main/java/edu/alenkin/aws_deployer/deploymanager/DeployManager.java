package edu.alenkin.aws_deployer.deploymanager;

import edu.alenkin.aws_deployer.ValidationException;
import edu.alenkin.aws_deployer.uploadutils.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.zip.ZipFile;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public interface DeployManager {
    UploadFileResponse upload(MultipartFile archive) throws ValidationException;
}
