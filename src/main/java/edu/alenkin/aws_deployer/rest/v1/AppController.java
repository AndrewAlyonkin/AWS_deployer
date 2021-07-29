package edu.alenkin.aws_deployer.rest.v1;

import edu.alenkin.aws_deployer.deploymanager.DeployManager;
import edu.alenkin.aws_deployer.upload_utils.UploadFileResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 * <p>
 * Controller consumes requests with archived java-project and deploy it to remote server.
 */
@RestController
@RequestMapping(AppController.REST_URL)
@Slf4j
public class AppController {
    public final static String REST_URL = "rest/v1/apps";

    private final DeployManager manager;

    @Autowired
    public AppController(DeployManager manager) {
        this.manager = manager;
    }

    /**
     * Request must contains in its body only value = ZIP archive, with key = "zip".
     * If the request contains a file of a different format, or the archive is marked with a incorrect
     * key - processing will fail.
     *
     * @param archive - the archive with valid java-project
     * @return the {@link UploadFileResponse} - the information about deployed project
     */
    @PostMapping
    public ResponseEntity pushApp(@RequestParam(name = "zip") MultipartFile archive) {
        log.info("POST rest/v1/apps - trying to upload project to S3");
        try {
            UploadFileResponse response = manager.upload(archive);
            log.info("Succesfuly uploaded {}", response.getFileName());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
