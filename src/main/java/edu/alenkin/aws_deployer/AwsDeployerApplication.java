package edu.alenkin.aws_deployer;

import edu.alenkin.aws_deployer.properties.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class AwsDeployerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsDeployerApplication.class, args);
    }

}
