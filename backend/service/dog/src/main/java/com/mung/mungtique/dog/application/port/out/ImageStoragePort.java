package com.mung.mungtique.dog.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStoragePort {

    String uploadDogImage(MultipartFile file, Long dogId);
}
