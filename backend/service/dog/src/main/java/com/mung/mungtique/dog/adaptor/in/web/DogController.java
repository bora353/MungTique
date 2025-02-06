package com.mung.mungtique.dog.adaptor.in.web;


import com.mung.mungtique.dog.adaptor.in.web.dto.image.ImageUploadReq;
import com.mung.mungtique.dog.adaptor.in.web.dto.image.ImageUploadRes;
import com.mung.mungtique.dog.adaptor.in.web.dto.mung.DogJoinReq;
import com.mung.mungtique.dog.adaptor.in.web.dto.mung.DogRes;
import com.mung.mungtique.dog.application.port.in.DogService;
import com.mung.mungtique.dog.application.port.in.ImageService;
import com.mung.mungtique.dog.domain.Dog;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
public class DogController {

    private final DogService dogService;
    private final ImageService imageService;

    @Operation(summary = "강아지 정보를 등록한다.")
    @PostMapping("/dogs")
    public ResponseEntity<Dog> likeMungShop(@RequestBody @Valid DogJoinReq mungJoinReq) {

        log.info("mungJoinReq" + mungJoinReq);
        return ResponseEntity.ok(dogService.join(mungJoinReq));
    }

    @Operation(summary = "강아지 이미지 업로드 한다.")
    @PostMapping("/dogs/upload-image")
    private ResponseEntity<ImageUploadRes> upload(@RequestBody @Valid ImageUploadReq imageUploadReq) throws IOException {
        log.info("imageUploadReq : {}", imageUploadReq);
        return ResponseEntity.ok(imageService.upload(imageUploadReq));
    }

    @Operation(summary = "로그인한 사용자의 강아지 정보를 모두 가져온다")
    @GetMapping("/dogs/{userId}")
    private ResponseEntity<List<DogRes>> getMyMungs(@PathVariable Long userId) {

        log.info("userId : {}", userId);
        return ResponseEntity.ok(dogService.getDogs(userId));
    }

}
