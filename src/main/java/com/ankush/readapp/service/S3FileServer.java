package com.ankush.readapp.service;

import com.ankush.readapp.utils.Utils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Paths;

@Service
@Slf4j
public class S3FileServer {

    private S3Client s3Client;

    @Value("${aws-properties.access-key}")
    private String accessKey;

    @Value("${aws-properties.secret-key}")
    private String secretKey;

    @Value("${aws-properties.region}")
    private String region;

    @Value("${aws-properties.bucket-name}")
    private String bucketName;

    @PostConstruct
    public void initS3Client() {
        var awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }

    public void uploadFileToS3(MultipartFile file, String bookId) {
        log.info("Uploading file to S3 file server having fileName: {}, bookId: {}", file.getOriginalFilename(), bookId);
        try {
            var filePath = generateFilePath(file.getOriginalFilename(), bookId);
            var putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filePath)
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        } catch (Exception e) {
            log.error("Failed to upload file to S3", e.getStackTrace());
            throw new RuntimeException("Failed to Upload File");
        }

        log.info("Successfully uploaded file: {} to server", file.getOriginalFilename());
    }

    private String generateFilePath(String fileName, String bookId) {
        return Paths.get(bookId, fileName.toLowerCase()).toString();
    }

}
