package edu.alenkin.aws_deployer.upload_utils;

import edu.alenkin.aws_deployer.entity.Project;

import java.io.IOException;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 *
 * Provides methods for manipulating {@link Project} files.
 */
public interface FileService {

    /**
     * Check existence of required files in target directory.
     * If current project directory does not contains required files - scans directory and searches project location,
     * and if the directory with wiles is found - swaps project path to current directory.
     *
     * @param project - the {@link Project}, in the directory of which the existence of files ic checked
     * @param fileName - the names of required files
     * @return true if files exists, or false, if at least one of the files is absent
     * @throws IOException because of problems with server file system
     */
    boolean isFilesExists(Project project, String... fileName) throws IOException;

    /**
     * Clears folder with project files.
     * @throws IOException
     */
    void clearStorageDir() throws IOException;
}
