package com.ankush.readapp.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

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

    /**
     * Uploads the book to S3 file server
     *
     * @param file The uploaded book
     * @param bookId The unique id associated with the book
     * @return The filename of the uploaded file
     */
    public String  uploadFileToS3(MultipartFile file, String bookId) {
        log.info("Uploading file to S3 file server having fileName: {}, bookId: {}", file.getOriginalFilename(), bookId);
        try {
            var fileName = generateFileName(file.getOriginalFilename(), bookId);
            var putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            log.info("Successfully uploaded file: {} to server", file.getOriginalFilename());
            return fileName;
        } catch (Exception e) {
            log.error("Failed to upload file to S3, stack trace: {}", e.getStackTrace());
            throw new RuntimeException("Failed to Upload File");
        }
    }

    public String fetchFileFromS3(String fileName) {
        log.info("Fetching file: {} from S3 File server", fileName);
        try {
            var getUrlRequest = GetUrlRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();
            return s3Client.utilities().getUrl(getUrlRequest).toExternalForm();
        } catch (Exception e) {
            log.error("Failed to fetch file from S3, stack trace: {}", e.getStackTrace());
            throw new RuntimeException("Failed to fetch file");
        }
    }

    private String generateFileName(String fileName, String bookId) {
        return Paths.get(bookId, fileName.toLowerCase()).toString();
    }

}
