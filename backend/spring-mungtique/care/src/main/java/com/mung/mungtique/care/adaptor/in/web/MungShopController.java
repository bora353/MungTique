package com.mung.mungtique.care.adaptor.in.web;

import com.mung.mungtique.care.adaptor.in.web.dto.MungShopRes;
import com.mung.mungtique.care.application.port.in.MungShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class MungShopController {

    private final MungShopService mungShopService;


    @GetMapping("/care/mungshops")
    public ResponseEntity<List<MungShopRes>> getAllMungShops() {

        return ResponseEntity.ok(mungShopService.getAllMungShops());
    }

}
