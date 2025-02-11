package com.mung.mungtique.dog.application.port.out;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageStoragePort {

    String uploadDogImage(MultipartFile file, Long dogId) throws IOException;
}
