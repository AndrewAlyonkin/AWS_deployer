package edu.alenkin.aws_deployer.validation_utils;

import edu.alenkin.aws_deployer.entity.Project;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public interface ValidationService {
    boolean isValid(Project project) throws IOException;
}
