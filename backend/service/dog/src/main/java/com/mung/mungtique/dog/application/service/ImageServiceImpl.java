package com.mung.mungtique.dog.application.service;


import com.mung.mungtique.dog.adaptor.in.web.dto.image.ImageUploadRes;
import com.mung.mungtique.dog.application.port.in.ImageService;
import com.mung.mungtique.dog.application.port.out.DogRepoPort;
import com.mung.mungtique.dog.application.port.out.ImageRepoPort;
import com.mung.mungtique.dog.application.port.out.ImageStoragePort;
import com.mung.mungtique.dog.domain.Dog;
import com.mung.mungtique.dog.domain.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {

    private final ImageRepoPort imageRepoPort;
    private final DogRepoPort dogRepoPort;
    private final ImageStoragePort imageStoragePort;

    @Override
    @Transactional
    public ImageUploadRes upload(Long dogId, MultipartFile imageFile) throws IOException {
        Dog dog = dogRepoPort.findById(dogId).orElseThrow(() -> new NoSuchElementException("Dog not found with ID: " + dogId));

        String uploadUrl = imageStoragePort.uploadDogImage(imageFile, dogId);
        log.info("Upload URL: {}", uploadUrl);

        Image image = imageRepoPort.findByDog(dog)
                .map(existingImage -> {
                    existingImage.updateUrl(uploadUrl);
                    return existingImage;
                })
                .orElseGet(() -> Image.builder()
                                        .dog(dog)
                                        .url(uploadUrl)
                                        .build());

        Image savedImage = imageRepoPort.save(image);
        log.info("Dog ID: {}, Saved Image: {}", dogId, savedImage);

        return new ImageUploadRes(uploadUrl);
    }
}
