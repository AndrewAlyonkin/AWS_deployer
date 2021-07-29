package edu.alenkin.aws_deployer.deploy;

import edu.alenkin.aws_deployer.entity.Project;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 *
 * The interface that response for uploading of the project on remote storage.
 */
public interface PushService {
    /**
     * Upload a {@link Project} to remote storage
     * @param project the valid project object, that contains path to project files in server file system
     * @return the URL of uploaded project, dy which it can be accessed by HTTP request.
     * @throws InterruptedException if the uploading to remote storage was interrupted with some cause.
     */
    String push(Project project) throws InterruptedException;
}
