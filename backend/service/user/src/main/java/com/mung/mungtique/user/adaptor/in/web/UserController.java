package com.mung.mungtique.user.adaptor.in.web;

import com.mung.mungtique.user.adaptor.in.web.dto.JoinReq;
import com.mung.mungtique.user.adaptor.in.web.dto.JoinRes;
import com.mung.mungtique.user.adaptor.in.web.dto.UserRes;
import com.mung.mungtique.user.application.port.in.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    @Operation(summary = "user 회원가입한다.")
    public ResponseEntity<JoinRes> registerUser(@RequestBody @Valid JoinReq joinReq){
        try {
            JoinRes joinRes = userService.createUser(joinReq);
            return ResponseEntity.ok(joinRes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "user 정보 가져온다.")
    public ResponseEntity<UserRes> getUserById(@PathVariable String userId){
        UserRes userInfo = userService.getUserInfo(userId);

        return ResponseEntity.ok(userInfo);
    }
}
