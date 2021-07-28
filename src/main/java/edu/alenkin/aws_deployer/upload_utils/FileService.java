package edu.alenkin.aws_deployer.upload_utils;

import edu.alenkin.aws_deployer.entity.Project;

import java.io.IOException;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public interface FileService {
    boolean isFilesExists(Project project, String... fileName) throws IOException;

    void clearStorageDir();
}
