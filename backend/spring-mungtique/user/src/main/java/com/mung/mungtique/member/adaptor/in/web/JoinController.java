package com.mung.mungtique.member.adaptor.in.web;

import com.mung.mungtique.member.adaptor.in.web.dto.JoinDTO;
import com.mung.mungtique.member.domain.User;
import com.mung.mungtique.member.application.port.in.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<User> joinProcess(@RequestBody JoinDTO joinDTO){
        User user = joinService.joinProcess(joinDTO);
        return ResponseEntity.ok().body(user);
    }

}
