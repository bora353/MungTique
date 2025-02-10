package com.mung.mungtique.dog.adaptor.out.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mung.mungtique.dog.application.port.out.ImageStoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ImageStorageAdaptor implements ImageStoragePort {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final static String DOG_IMAGE_DIRECTORY = "dog/";

    public String uploadDogImage(MultipartFile file, Long dogId) {
        String fileName = DOG_IMAGE_DIRECTORY + dogId + "/" + UUID.randomUUID() + file.getName();
        return uploadToS3(file, fileName);
    }

    private String uploadToS3(MultipartFile file, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, String.valueOf(file))
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }
}
