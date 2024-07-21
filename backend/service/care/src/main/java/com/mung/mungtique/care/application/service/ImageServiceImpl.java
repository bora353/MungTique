package com.mung.mungtique.care.application.service;

import com.mung.mungtique.care.adaptor.in.web.dto.image.ImageUploadReq;
import com.mung.mungtique.care.adaptor.in.web.dto.image.ImageUploadRes;
import com.mung.mungtique.care.application.port.in.ImageService;
import com.mung.mungtique.care.application.port.out.ImagePort;
import com.mung.mungtique.care.application.port.out.MungPort;
import com.mung.mungtique.care.domain.Image;
import com.mung.mungtique.care.domain.MyMung;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final ImagePort imagePort;
    private final MungPort mungPort;

    @Value("${file.path.image}")
    private String uploadFolder;

    @Override
    public ImageUploadRes upload(ImageUploadReq imageUploadReq) throws IOException {
        Long mungId = imageUploadReq.mungId();
        MultipartFile file = imageUploadReq.file();
        MyMung myMung = mungPort.findByid(mungId).orElseThrow(() -> new NoSuchElementException("Could not find Mung with the given ID"));

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + file.getOriginalFilename();

        // TODO : Object Storage 사용하자
        File destinationFile = new File(uploadFolder + imageFileName);
        log.info("destinationFile : {}", destinationFile);

        file.transferTo(destinationFile);

        Image image = imagePort.findByMyMung(myMung);
        log.info("image : {} ", image);

        if (image != null) {
            image.updateUrl("/mungImages/" + imageFileName);
        } else {
            image = Image.builder()
                    .myMung(myMung)
                    .url("/mungImages/" + imageFileName)
                    .build();
        }
        Image savedImage = imagePort.save(image);
        log.info("Saved Image : {}", savedImage);

        return new ImageUploadRes("/mungImages/" + imageFileName);
    }
}
