package edu.alenkin.aws_deployer.deploy;

import edu.alenkin.aws_deployer.entity.Project;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public interface PushService {
    String push(Project project);
}
