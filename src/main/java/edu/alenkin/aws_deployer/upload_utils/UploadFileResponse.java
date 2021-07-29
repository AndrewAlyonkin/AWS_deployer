package edu.alenkin.aws_deployer.upload_utils;

import lombok.Getter;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 *
 * Contains the information about deployed project.
 */
@Getter
public class UploadFileResponse {
    private String fileName;
    private String fileDownloadUri;
    private String archiveName;
    private long size;

    private UploadFileResponse(String fileName, String fileDownloadUri, String archiveName, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.archiveName = archiveName;
        this.size = size;
    }

    public UploadFileResponse withProjectName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public UploadFileResponse withUrl(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
        return this;
    }

    public UploadFileResponse fromArchiveNamed(String archiveName) {
        this.archiveName = archiveName;
        return this;
    }

    public UploadFileResponse withSize(long size) {
        this.size = size;
        return this;
    }

    public static UploadFileResponse createResponse() {
        return new UploadFileResponse(null, null, null, 0);
    }
}
