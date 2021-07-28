package edu.alenkin.aws_deployer.entity;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class Project {
    private String name;
    private String path;
    private int size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
