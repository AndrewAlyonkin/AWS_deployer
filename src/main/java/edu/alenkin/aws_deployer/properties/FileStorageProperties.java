package edu.alenkin.aws_deployer.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
