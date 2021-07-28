package edu.alenkin.aws_deployer.deploy;

import edu.alenkin.aws_deployer.entity.Project;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class AwsPushService implements PushService{
    @Override
    public String push(Project project) {
        System.out.println("Pushing to AWS!!!!!");
        return "dummy"; //TODO
    }

    private String createDir(Project project) {
        return "dummy";  //TODO
    }
}
