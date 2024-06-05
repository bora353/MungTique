package com.mung.mungtique.care.adaptor.in.web;

import com.mung.mungtique.care.adaptor.in.web.dto.ImageUploadReq;
import com.mung.mungtique.care.adaptor.in.web.dto.ImageUploadRes;
import com.mung.mungtique.care.adaptor.in.web.dto.MungJoinReq;
import com.mung.mungtique.care.application.port.in.ImageService;
import com.mung.mungtique.care.application.port.in.MungService;
import com.mung.mungtique.care.domain.MyMung;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class MungController {

    private final MungService mungService;
    private final ImageService imageService;

    @PostMapping("/care/join/mung")
    public ResponseEntity<MyMung> likeMungShop(@RequestBody MungJoinReq mungJoinReq) {

        log.info("mungJoinReq" + mungJoinReq);
        return ResponseEntity.ok(mungService.join(mungJoinReq));
    }

    @PostMapping("/care/upload-image")
    private ResponseEntity<ImageUploadRes> upload(ImageUploadReq imageUploadReq) throws IOException {
        // TODO : 여기서 try-catch할지 throw할지 고민해보자!

        return ResponseEntity.ok(imageService.upload(imageUploadReq));
    }
}
