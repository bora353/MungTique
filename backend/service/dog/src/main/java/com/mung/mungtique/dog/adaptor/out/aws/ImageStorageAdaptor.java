package com.mung.mungtique.dog.adaptor.out.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mung.mungtique.dog.application.port.out.ImageStoragePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImageStorageAdaptor implements ImageStoragePort {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final static String DOG_IMAGE_DIRECTORY = "dog/";

    public String uploadDogImage(MultipartFile file, Long dogId) throws IOException {
        String fileName = DOG_IMAGE_DIRECTORY + dogId + "/" + UUID.randomUUID() + file.getOriginalFilename();
        log.info("Generated file name: {}", fileName);

        return uploadToS3(file, fileName);
    }

    private String uploadToS3(MultipartFile file, String fileName) throws IOException {
        long fileSize = file.getSize();
        log.info("Uploading file to S3: {}", fileName);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileSize);

        PutObjectRequest request = new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata);

        amazonS3Client.putObject(request);

        log.info("File successfully uploaded to S3: {}", fileName);
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }
}
