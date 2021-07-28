package edu.alenkin.aws_deployer.entity;

import java.nio.file.Path;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class Project {
    private String name;
    private Path path;
    private long size;

    public Project(String name, Path path, long size) {
        this.name = name;
        this.path = path;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
