package edu.alenkin.aws_deployer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 * <p>
 * Representation of the java project entity.
 * Contains information about it name, size and path to files on server file system.
 */
@Getter
@Setter
@AllArgsConstructor
public class Project {
    private String name;
    private Path path;
    private long size;
}
