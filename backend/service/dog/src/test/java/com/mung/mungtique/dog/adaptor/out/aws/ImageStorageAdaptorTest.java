package com.mung.mungtique.dog.adaptor.out.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ImageStorageAdaptorTest {

    @InjectMocks
    private ImageStorageAdaptor imageStorageAdaptor;
    @Mock
    private AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket = "test-bucket";


}