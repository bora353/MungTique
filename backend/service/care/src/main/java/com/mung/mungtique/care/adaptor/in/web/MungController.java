package com.mung.mungtique.care.adaptor.in.web;

import com.mung.mungtique.care.adaptor.in.web.dto.image.ImageUploadReq;
import com.mung.mungtique.care.adaptor.in.web.dto.image.ImageUploadRes;
import com.mung.mungtique.care.adaptor.in.web.dto.mung.MungJoinReq;
import com.mung.mungtique.care.adaptor.in.web.dto.mung.MungRes;
import com.mung.mungtique.care.application.port.in.ImageService;
import com.mung.mungtique.care.application.port.in.MungService;
import com.mung.mungtique.care.domain.MyMung;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/care-service")
public class MungController {

    private final MungService mungService;
    private final ImageService imageService;

    @Operation(summary = "강아지 정보를 등록한다.")
    @PostMapping("/join/mung")
    public ResponseEntity<MyMung> likeMungShop(@RequestBody MungJoinReq mungJoinReq) {

        log.info("mungJoinReq" + mungJoinReq);
        return ResponseEntity.ok(mungService.join(mungJoinReq));
    }

    @Operation(summary = "강아지 이미지 업로드 한다.")
    @PostMapping("/upload-image")
    private ResponseEntity<ImageUploadRes> upload(ImageUploadReq imageUploadReq) throws IOException {
        log.info("imageUploadReq : {}", imageUploadReq);
        return ResponseEntity.ok(imageService.upload(imageUploadReq));
    }

    @Operation(summary = "로그인한 사용자의 강아지 정보를 모두 가져온다")
    @GetMapping("/mymung/{userId}")
    private ResponseEntity<List<MungRes>> getMyMungs(@PathVariable Long userId) {

        log.info("userId : {}", userId);
        return ResponseEntity.ok(mungService.getMyMungs(userId));
    }

}
