package com.mung.mungtique.care.adaptor.in.web;

import com.mung.mungtique.care.adaptor.in.web.dto.image.ImageUploadReq;
import com.mung.mungtique.care.adaptor.in.web.dto.image.ImageUploadRes;
import com.mung.mungtique.care.adaptor.in.web.dto.mung.MungJoinReq;
import com.mung.mungtique.care.adaptor.in.web.dto.mung.MungRes;
import com.mung.mungtique.care.application.port.in.ImageService;
import com.mung.mungtique.care.application.port.in.MungService;
import com.mung.mungtique.care.domain.MyMung;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
        log.info("imageUploadReq : {}", imageUploadReq);
        return ResponseEntity.ok(imageService.upload(imageUploadReq));
    }

    @GetMapping("/care/mymung/{userId}")
    private ResponseEntity<List<MungRes>> getMyMungs(@PathVariable Long userId) {

        log.info("userId : {}", userId);
        return ResponseEntity.ok(mungService.getMyMungs(userId));
    }

}
