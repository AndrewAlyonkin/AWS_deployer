package edu.alenkin.aws_deployer.uploadutils;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class UploadFileResponse {
    private String fileName;
    private String fileDownloadUri;
    private String archiveName;
    private long size;

    public UploadFileResponse(String fileName, String fileDownloadUri, String archiveName, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.archiveName = archiveName;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getArchiveName() {
        return archiveName;
    }

    public void setArchiveName(String archiveName) {
        this.archiveName = archiveName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
